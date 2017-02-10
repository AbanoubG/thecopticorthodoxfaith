package org.copticchurch.library.discoverorthodoxy;

import android.app.Application;
import android.content.Context;

import org.copticchurch.library.discoverorthodoxy.database.CompletionDataSource;
import org.copticchurch.library.discoverorthodoxy.database.UserDataSource;
import org.copticchurch.library.discoverorthodoxy.logic.CompletionLogic;
import org.copticchurch.library.discoverorthodoxy.logic.SettingsLogic;
import org.copticchurch.library.discoverorthodoxy.logic.UserLogicFactory;
import org.copticchurch.library.discoverorthodoxy.logic.UserManager;
import org.copticchurch.library.discoverorthodoxy.logic.statistics.ChartSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import org.copticchurch.library.discoverorthodoxy.activities.playchallenge.AnswerFragmentFactory;

/**
 * Created by funkv on 06.03.2016.
 *
 * Defines how instances are created for the App
 */
@Module
public class AppModule {
    BrainPhaserApplication mApplication;

    public AppModule(BrainPhaserApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    BrainPhaserApplication providesBpApp() {
        return mApplication;
    }

    @Provides
    @Singleton
    UserManager providesUserManager(Application application, UserDataSource userDataSource) {
        return new UserManager(application, userDataSource);
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    UserLogicFactory providesUserLogic(BrainPhaserApplication app) {
        UserLogicFactory factory =  new UserLogicFactory();
        app.getComponent().inject(factory);
        return factory;
    }

    @Provides
    @Singleton
    ChartSettings providesChartSettings(BrainPhaserApplication app) {
        return new ChartSettings(app);
    }

    @Provides
    @Singleton
    SettingsLogic providesSettingsLogic() {
        return new SettingsLogic();
    }

    @Provides
    @Singleton
    AnswerFragmentFactory providesFragmentFactory() { return new AnswerFragmentFactory(); }

    @Provides
    @Singleton
    CompletionLogic providesCompletionLogic(CompletionDataSource ds) {
        return new CompletionLogic(ds);
    }
}
