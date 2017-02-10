package org.copticchurch.library.discoverorthodoxy.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import org.copticchurch.library.discoverorthodoxy.model.UserDao;
import org.copticchurch.library.discoverorthodoxy.model.CategoryDao;
import org.copticchurch.library.discoverorthodoxy.model.ChallengeDao;
import org.copticchurch.library.discoverorthodoxy.model.AnswerDao;
import org.copticchurch.library.discoverorthodoxy.model.CompletionDao;
import org.copticchurch.library.discoverorthodoxy.model.SettingsDao;
import org.copticchurch.library.discoverorthodoxy.model.StatisticsDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        UserDao.createTable(db, ifNotExists);
        CategoryDao.createTable(db, ifNotExists);
        ChallengeDao.createTable(db, ifNotExists);
        AnswerDao.createTable(db, ifNotExists);
        CompletionDao.createTable(db, ifNotExists);
        SettingsDao.createTable(db, ifNotExists);
        StatisticsDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        UserDao.dropTable(db, ifExists);
        CategoryDao.dropTable(db, ifExists);
        ChallengeDao.dropTable(db, ifExists);
        AnswerDao.dropTable(db, ifExists);
        CompletionDao.dropTable(db, ifExists);
        SettingsDao.dropTable(db, ifExists);
        StatisticsDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(UserDao.class);
        registerDaoClass(CategoryDao.class);
        registerDaoClass(ChallengeDao.class);
        registerDaoClass(AnswerDao.class);
        registerDaoClass(CompletionDao.class);
        registerDaoClass(SettingsDao.class);
        registerDaoClass(StatisticsDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
