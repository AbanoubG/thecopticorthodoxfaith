package org.copticchurch.library.discoverorthodoxy.selectcategorytest;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.SparseArray;
import android.view.View;

import org.copticchurch.library.discoverorthodoxy.BrainPhaserApplication;
import org.copticchurch.library.discoverorthodoxy.BrainPhaserComponent;
import org.copticchurch.library.discoverorthodoxy.DaggerActivityTestRule;
import org.copticchurch.library.discoverorthodoxy.TestAppModule;
import org.copticchurch.library.discoverorthodoxy.TestBrainPhaserApplication;
import org.copticchurch.library.discoverorthodoxy.TestUtils;
import org.copticchurch.library.discoverorthodoxy.activities.main.MainActivity;
import org.copticchurch.library.discoverorthodoxy.activities.selectcategory.CategoryViewHolder;
import org.copticchurch.library.discoverorthodoxy.database.CategoryDataSource;
import org.copticchurch.library.discoverorthodoxy.database.MockDatabaseModule;
import org.copticchurch.library.discoverorthodoxy.logic.UserLogicFactory;
import org.copticchurch.library.discoverorthodoxy.model.User;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

import org.copticchurch.library.discoverorthodoxy.logic.DueChallengeLogic;
import org.copticchurch.library.discoverorthodoxy.model.Category;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by funkv on 05.03.2016.
 *
 * Tests the category selection page
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CategorySelectionTest {
    @Inject
    UserLogicFactory mockLogicFactory;
    @Inject
    CategoryDataSource mockCategoryDataSource;
    DueChallengeLogic mockDueChallengeLogic;
    private TestAppComponent mTestAppComponent;
    private SparseArray<Category> mPositions = new SparseArray<>();
    private List<Category> mFakeCategories;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
        new DaggerActivityTestRule<>(MainActivity.class, new DaggerActivityTestRule.OnBeforeActivityLaunchedListener<MainActivity>() {
            @Override
            public void beforeActivityLaunched(@NonNull Application application, @NonNull MainActivity activity) {
                mTestAppComponent = DaggerCategorySelectionTest_TestAppComponent.builder()
                    .testAppModule(new TestAppModule((BrainPhaserApplication) application))
                    .mockDatabaseModule(new MockDatabaseModule(application.getApplicationContext(), "TEST"))
                    .build();

                ((TestBrainPhaserApplication) application).setTestComponent(mTestAppComponent);
                setUp();
            }
        });

    /**
     * Matches a view Holder whose title is catName
     *
     * @param catName category name that should be the viewHolders title.
     * @return created matcher
     */
    public static Matcher<RecyclerView.ViewHolder> categoryVH(final String catName) {
        return new BoundedMatcher<RecyclerView.ViewHolder, CategoryViewHolder>(CategoryViewHolder.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("category title is " + catName);
            }

            @Override
            protected boolean matchesSafely(CategoryViewHolder item) {
                return item.getTitle().getText().equals(catName);
            }
        };
    }

    /**
     * Sets up fake returns for the data sources
     */
    public void setUp() {
        mTestAppComponent.inject(this);

        // Fake categories
        mFakeCategories = Arrays.asList(
            new Category(1L, "Englisch", "Verbessere deine Englischkenntnisse und dein Wissen über Englischsprachige Länder. Lerne nützliche Phrasen und Umgangsformen.", "@drawable/englisch"),
            new Category(2L, "Architektur", "Verbessere dein Wissen über berühmte Gebäude, Bauarten und Architekturepochen..", "@drawable/architektur"),
            new Category(3L, "Computer", "Lerne neue, coole Fakten über Computer und Informationstechnologie. Du wirst mit Fragen zu Netzwerken, Hardware, Software, Programmiersprachen und Softwareprojekten getestet.", "@drawable/computer"),
            new Category(4L, "Geographie", "Länder, Kulturen und Traditionen.", "@drawable/englisch")
        );
        Mockito.when(mockCategoryDataSource.getAll()).thenReturn(
            mFakeCategories
        );

        // How many due challenges in above categories
        LongSparseArray<Integer> fakeCounts = new LongSparseArray<>();
        fakeCounts.put(1, 10);
        fakeCounts.put(2, 0);
        fakeCounts.put(3, 11);
        fakeCounts.put(4, 3);
        mockDueChallengeLogic = Mockito.mock(DueChallengeLogic.class);
        Mockito.when(mockDueChallengeLogic.getDueChallengeCounts(Mockito.anyListOf(Category.class))).thenReturn(
            fakeCounts
        );

        Mockito.when(mockLogicFactory.createDueChallengeLogic(org.mockito.Matchers.any(User.class)))
            .thenReturn(mockDueChallengeLogic);

        // Which category should be at which position (pos -> categoryId)
        mPositions.put(4, mFakeCategories.get(1));
        mPositions.put(3, mFakeCategories.get(3));
        mPositions.put(2, mFakeCategories.get(0));
        mPositions.put(1, mFakeCategories.get(2));
    }

    /**
     * Tests that the first element in the selection is the "All Categories" button
     */
    @Test
    public void checkFirstElementAllCategories() {
        onView(allOf(ViewMatchers.withId(org.copticchurch.library.discoverorthodoxy.R.id.select_category_fragment), withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(TestUtils.atPosition(0, notNullValue(View.class))))
            .check(matches(TestUtils.atPosition(0, hasDescendant(ViewMatchers.withText(org.copticchurch.library.discoverorthodoxy.R.string.all_categories)))));
    }

    /**
     * Tests that all categories are displayed in the list.
     */
    @Test
    public void checkAllCategoriesShown() {
        for (Category c : mFakeCategories) {
            Matcher<View> hasDescendantTitle = hasDescendant(withText(c.getTitle()));
            onView(allOf(ViewMatchers.withId(org.copticchurch.library.discoverorthodoxy.R.id.select_category_fragment), withEffectiveVisibility(Visibility.VISIBLE)))
                .perform(RecyclerViewActions.scrollToHolder(categoryVH(c.getTitle())))
                .check(matches(hasDescendantTitle));
        }
    }

    /**
     * Tests that the list ordering is correct (cards with more due challenges are further up in the list)
     */
    @Test
    public void checkListOrdering() {
        for (int i = 0; i < mPositions.size(); i++) {
            int position = mPositions.keyAt(i);

            String categoryName = mPositions.get(position).getTitle();
            Matcher<View> hasDescendantTitle = hasDescendant(withText(categoryName));

            onView(allOf(ViewMatchers.withId(org.copticchurch.library.discoverorthodoxy.R.id.select_category_fragment), withEffectiveVisibility(Visibility.VISIBLE)))
                .perform(RecyclerViewActions.scrollToHolder(categoryVH(categoryName)))
                .check(matches(TestUtils.atPosition(position, hasDescendantTitle)));
        }
    }

    /**
     * Component that uses the mock modules to fake data from the database
     */
    @Singleton
    @Component(modules = {TestAppModule.class, MockDatabaseModule.class})
    interface TestAppComponent extends BrainPhaserComponent {
        void inject(CategorySelectionTest test);
    }
}
