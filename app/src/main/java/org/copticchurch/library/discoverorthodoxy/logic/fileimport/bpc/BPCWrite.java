package org.copticchurch.library.discoverorthodoxy.logic.fileimport.bpc;

import org.copticchurch.library.discoverorthodoxy.BrainPhaserApplication;
import org.copticchurch.library.discoverorthodoxy.database.AnswerDataSource;
import org.copticchurch.library.discoverorthodoxy.database.ChallengeDataSource;
import org.copticchurch.library.discoverorthodoxy.model.Answer;
import org.copticchurch.library.discoverorthodoxy.model.Challenge;

import java.util.List;

import javax.inject.Inject;

import org.copticchurch.library.discoverorthodoxy.database.CategoryDataSource;
import org.copticchurch.library.discoverorthodoxy.model.Category;

/**
 * Created by Daniel Hoogen on 25/02/2016.
 */
public class BPCWrite {
    //Attributes
    @Inject
    CategoryDataSource mCategoryDataSource;
    @Inject
    ChallengeDataSource mChallengeDataSource;
    @Inject
    AnswerDataSource mAnswerDataSource;

    /**
     * Constructor which saves the given parameters as member attributes.
     *
     * @param application the BrainPhaserApplication to be saved as a member attribute
     */
    public BPCWrite(BrainPhaserApplication application) {
        application.getComponent().inject(this);
    }

    /**
     * Writes all categories with their challenges and their answers to the database
     *
     * @param categoryList  the list of categories to be written to the database
     * @param challengeList the list of challenges to be written to the database
     * @param answerList    the list of answers to be written to the database
     */
    public void writeAll(List<Category> categoryList, List<Challenge> challengeList, List<Answer> answerList) {
        for (Category category : categoryList) {
            writeCategory(category, challengeList, answerList);
        }
    }

    /**
     * Writes a category with their challenges and their answers to the database
     *
     * @param category      the category to be written to the database
     * @param challengeList the list of challenges to be written to the database
     * @param answerList    the list of answers to be written to the database
     */
    private void writeCategory(Category category, List<Challenge> challengeList, List<Answer> answerList) {
        long oldCategoryId = category.getId();
        category.setId(null);
        long categoryId = mCategoryDataSource.create(category);

        for (int i = 0; i < challengeList.size(); i++) {
            Challenge challenge = challengeList.get(i);
            if (challenge != null && challenge.getCategoryId() == oldCategoryId) {
                challenge.setCategoryId(categoryId);
                writeChallenge(challenge, answerList);
                challengeList.set(i, null);
            }
        }
    }

    /**
     * Writes a challenge with their answers to the database
     *
     * @param challenge  the challenge to be written to the database
     * @param answerList the list of answers to be written to the database
     */
    private void writeChallenge(Challenge challenge, List<Answer> answerList) {
        long oldChallengeId = challenge.getId();
        challenge.setId(null);
        long challengeId = mChallengeDataSource.create(challenge);

        for (Answer answer : answerList) {
            if (answer.getChallengeId() == oldChallengeId) {
                answer.setChallengeId(challengeId);
                writeAnswer(answer);
                answer.setChallengeId(-1);
            }
        }
    }

    /**
     * Writes an answer to the database
     *
     * @param answer the answer to be written to the database
     */
    private void writeAnswer(Answer answer) {
        answer.setId(null);
        mAnswerDataSource.create(answer);
    }
}
