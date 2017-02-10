package org.copticchurch.library.discoverorthodoxy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.copticchurch.library.discoverorthodoxy.model.DaoMaster;
import org.copticchurch.library.discoverorthodoxy.model.DaoSession;
import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by funkv on 07.03.2016.
 */
@Module
public class MockDatabaseModule {
    DaoMaster.DevOpenHelper mDevOpenHelper;
    SQLiteDatabase mDatabase;

    public MockDatabaseModule(Context context, String databaseName) {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, databaseName, null);
        mDatabase = mDevOpenHelper.getWritableDatabase();
    }

    @Provides
    @Singleton
    DaoSession provideSession() {
        DaoMaster daoMaster = new DaoMaster(mDatabase);
        return daoMaster.newSession();
    }

    @Provides
    @Singleton
    SQLiteDatabase provideDatabase() {
        return mDevOpenHelper.getWritableDatabase();
    }

    @Provides
    @Singleton
    AnswerDataSource provideAnswerDataSource(DaoSession session) {
        return new AnswerDataSource(session);
    }

    @Provides
    @Singleton
    ChallengeDataSource provideChallengeDataSource(DaoSession session) {
        return new ChallengeDataSource(session);
    }

    @Provides
    @Singleton
    CompletionDataSource provideCompletionDataSource(DaoSession session) {
        return new CompletionDataSource(session);
    }

    @Provides
    @Singleton
    SettingsDataSource provideSettingsDataSource(DaoSession session) {
        return new SettingsDataSource(session);
    }

    @Provides
    @Singleton
    UserDataSource provideUserDataSource(DaoSession session, SettingsDataSource settingsDataSource) {
        return new UserDataSource(session, settingsDataSource);
    }

    @Provides
    @Singleton
    CategoryDataSource provideCategoryDataSource(DaoSession session) {
        return Mockito.mock(CategoryDataSource.class);
    }
}
