package org.copticchurch.library.discoverorthodoxy;

import org.copticchurch.library.discoverorthodoxy.activities.createuser.CreateUserActivity;
import org.copticchurch.library.discoverorthodoxy.activities.main.MainActivity;
import org.copticchurch.library.discoverorthodoxy.activities.main.ProxyActivity;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.AnswerFragment;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.multiplechoice.MultipleChoiceFragment;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.selfcheck.SelfTestFragment;
import org.copticchurch.library.discoverorthodoxy.activities.selectuser.UserAdapter;
import org.copticchurch.library.discoverorthodoxy.activities.selectuser.UserSelectionActivity;
import org.copticchurch.library.discoverorthodoxy.logic.UserLogicFactory;
import org.copticchurch.library.discoverorthodoxy.activities.aboutscreen.AboutActivity;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.ChallengeActivity;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.multiplechoice.ButtonViewState;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.text.TextFragment;
import org.copticchurch.library.discoverorthodoxy.activities.selectcategory.SelectCategoryPage;
import org.copticchurch.library.discoverorthodoxy.activities.statistics.StatisticsActivity;
import org.copticchurch.library.discoverorthodoxy.activities.usersettings.SettingsActivity;
import org.copticchurch.library.discoverorthodoxy.logic.fileimport.bpc.BPCWrite;

/**
 * Created by funkv on 06.03.2016.
 *
 * App Component that defines injection targets for DI.
 */
public interface BrainPhaserComponent {
    void inject(MainActivity mainActivity);
    void inject(ProxyActivity activity);
    void inject(ChallengeActivity challengeActivity);
    void inject(MultipleChoiceFragment questionFragment);
    void inject(TextFragment textFragment);
    void inject(SelfTestFragment selfTestFragment);
    void inject(CreateUserActivity createUserActivity);
    void inject(UserAdapter userAdapter);
    void inject(UserSelectionActivity activity);
    void inject(StatisticsActivity activity);
    void inject(SettingsActivity activity);

    void inject(AboutActivity activity);

    void inject(SelectCategoryPage selectCategoryPage);
    void inject(AnswerFragment answerFragment);

    void inject(BPCWrite bpcWrite);
    void inject(ButtonViewState state);

    void inject(UserLogicFactory f);
}
