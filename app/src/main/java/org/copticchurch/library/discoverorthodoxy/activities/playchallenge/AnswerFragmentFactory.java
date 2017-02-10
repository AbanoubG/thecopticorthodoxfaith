package org.copticchurch.library.discoverorthodoxy.activities.playchallenge;

import org.copticchurch.library.discoverorthodoxy.BuildConfig;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.multiplechoice.MultipleChoiceFragment;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.selfcheck.SelfTestFragment;
import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.text.TextFragment;
import org.copticchurch.library.discoverorthodoxy.database.ChallengeType;

import java.util.HashMap;

/**
 * Created by Christian Kost
 * </p>
 * Abstracts and bundles the creation of Fragments for Challenge Types.
 */
public class AnswerFragmentFactory {
    private static HashMap<Integer, AnswerFragmentCreator> challengeTypeFactories;

    static {
        challengeTypeFactories = new HashMap<>();
        challengeTypeFactories.put(ChallengeType.MULTIPLE_CHOICE, new AnswerFragmentCreator() {
            @Override
            public AnswerFragment createFragment() {
                return new MultipleChoiceFragment();
            }
        });
        challengeTypeFactories.put(ChallengeType.TEXT, new AnswerFragmentCreator() {
            @Override
            public AnswerFragment createFragment() {
                return new TextFragment();
            }
        });
        challengeTypeFactories.put(ChallengeType.SELF_TEST, new AnswerFragmentCreator() {
            @Override
            public AnswerFragment createFragment() {
                return new SelfTestFragment();
            }
        });
    }

    /**
     * Creates a fragment for the specified challengeType.
     *
     * @param challengeType one of ChallengeType.*
     * @return newly created fragment.
     */
    public AnswerFragment createFragmentForType(int challengeType) {
        AnswerFragmentCreator creator = challengeTypeFactories.get(challengeType);
        if (BuildConfig.DEBUG && creator == null) {
            throw new RuntimeException("Invalid Challenge Type " + challengeType);
        }

        return creator.createFragment();
    }

    /**
     * Interface to create an AnswerFragment
     */
    private interface AnswerFragmentCreator {
        AnswerFragment createFragment();
    }
}
