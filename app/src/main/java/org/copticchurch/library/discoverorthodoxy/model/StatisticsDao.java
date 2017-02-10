package org.copticchurch.library.discoverorthodoxy.model;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import org.copticchurch.library.discoverorthodoxy.model.Statistics;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STATISTICS".
*/
public class StatisticsDao extends AbstractDao<Statistics, Long> {

    public static final String TABLENAME = "STATISTICS";

    /**
     * Properties of entity Statistics.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Succeeded = new Property(1, Boolean.class, "succeeded", false, "SUCCEEDED");
        public final static Property Time = new Property(2, java.util.Date.class, "time", false, "TIME");
        public final static Property UserId = new Property(3, long.class, "userId", false, "USER_ID");
        public final static Property ChallengeId = new Property(4, long.class, "challengeId", false, "CHALLENGE_ID");
    };

    private DaoSession daoSession;

    private Query<Statistics> user_StatisticsQuery;

    public StatisticsDao(DaoConfig config) {
        super(config);
    }
    
    public StatisticsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STATISTICS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SUCCEEDED\" INTEGER," + // 1: succeeded
                "\"TIME\" INTEGER," + // 2: time
                "\"USER_ID\" INTEGER NOT NULL ," + // 3: userId
                "\"CHALLENGE_ID\" INTEGER NOT NULL );"); // 4: challengeId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STATISTICS\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Statistics entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Boolean succeeded = entity.getSucceeded();
        if (succeeded != null) {
            stmt.bindLong(2, succeeded ? 1L: 0L);
        }
 
        java.util.Date time = entity.getTime();
        if (time != null) {
            stmt.bindLong(3, time.getTime());
        }
        stmt.bindLong(4, entity.getUserId());
        stmt.bindLong(5, entity.getChallengeId());
    }

    @Override
    protected void attachEntity(Statistics entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Statistics readEntity(Cursor cursor, int offset) {
        Statistics entity = new Statistics( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // succeeded
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // time
            cursor.getLong(offset + 3), // userId
            cursor.getLong(offset + 4) // challengeId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Statistics entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSucceeded(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setTime(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setUserId(cursor.getLong(offset + 3));
        entity.setChallengeId(cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Statistics entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Statistics entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "statistics" to-many relationship of User. */
    public List<Statistics> _queryUser_Statistics(long userId) {
        synchronized (this) {
            if (user_StatisticsQuery == null) {
                QueryBuilder<Statistics> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                user_StatisticsQuery = queryBuilder.build();
            }
        }
        Query<Statistics> query = user_StatisticsQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getChallengeDao().getAllColumns());
            builder.append(" FROM STATISTICS T");
            builder.append(" LEFT JOIN CHALLENGE T0 ON T.\"CHALLENGE_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Statistics loadCurrentDeep(Cursor cursor, boolean lock) {
        Statistics entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Challenge statistic = loadCurrentOther(daoSession.getChallengeDao(), cursor, offset);
         if(statistic != null) {
            entity.setStatistic(statistic);
        }

        return entity;    
    }

    public Statistics loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Statistics> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Statistics> list = new ArrayList<Statistics>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Statistics> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Statistics> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
