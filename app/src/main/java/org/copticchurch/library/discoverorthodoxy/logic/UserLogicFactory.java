package org.copticchurch.library.discoverorthodoxy.logic;

import org.copticchurch.library.discoverorthodoxy.model.User;

import javax.inject.Inject;

import org.copticchurch.library.discoverorthodoxy.BrainPhaserApplication;
import org.copticchurch.library.discoverorthodoxy.database.ChallengeDataSource;
import org.copticchurch.library.discoverorthodoxy.database.CompletionDataSource;
import org.copticchurch.library.discoverorthodoxy.database.StatisticsDataSource;
import org.copticchurch.library.discoverorthodoxy.logic.statistics.ChartDataLogic;
import org.copticchurch.library.discoverorthodoxy.logic.statistics.ChartSettings;
import org.copticchurch.library.discoverorthodoxy.logic.statistics.StatisticsLogic;

/**
 * Created by funkv on 06.03.2016.
 * <p/>
 * Factory that is used to create logic objects which require a user.
 * Dependencies are injected automatically.
 */
public class UserLogicFactory {
    @Inject
    BrainPhaserApplication mApplication;
    @Inject
    CompletionDataSource mCompletionDataSource;
    @Inject
    ChallengeDataSource mChallengeDataSource;
    @Inject
    StatisticsDataSource mStatisticsDataSource;
    @Inject
    ChartSettings mSettings;

    /**
     * Create a DueChallengeLogic for the specified user.
     *
     * @param user user whose challenges are inspected
     * @return the DueChallengeLogic object
     */
    public DueChallengeLogic createDueChallengeLogic(User user) {
        return new DueChallengeLogic(user, mCompletionDataSource, mChallengeDataSource);
    }

    /**
     * Creates ChartDataLogic
     *
     * @param user
     * @param categoryId category to inspect
     * @return ChartDataLogic
     */
    public ChartDataLogic createChartDataLogic(User user, long categoryId) {
        return new ChartDataLogic(user,
                categoryId,
                mApplication,
                mChallengeDataSource,
                mCompletionDataSource,
                mStatisticsDataSource,
                this);
    }

    /**
     * Create a DueChallengeLogic for the specified user.
     *
     * @param user       user whose challenges are inspected
     * @param categoryId category to inspect
     * @return the DueChallengeLogic object
     */
    public StatisticsLogic createStatisticsLogic(User user, long categoryId) {
        return new StatisticsLogic(mApplication, mSettings, createChartDataLogic(user, categoryId));
    }
}
