package org.copticchurch.library.discoverorthodoxy.database;

import org.copticchurch.library.discoverorthodoxy.model.Answer;
import org.copticchurch.library.discoverorthodoxy.model.DaoSession;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Daniel Hoogen on 25/02/2016.
 * <p/>
 * Data Source class for custom access to answer table entries in the database
 */
public class AnswerDataSource {
    //Attributes
    private DaoSession mDaoSession;

    /**
     * Constructor which saves all given parameters to local member attributes.
     *
     * @param session the session to be saved as a member attribute
     */
    @Inject
    AnswerDataSource(DaoSession session) {
        mDaoSession = session;
    }

    /**
     * Return all Answer objects in the answer table
     *
     * @return List object containing all Answer objects
     */
    public List<Answer> getAll() {
        return mDaoSession.getAnswerDao().loadAll();
    }

    /**
     * Returns the Answer object with the given id
     *
     * @param id answer id in the database
     * @return Answer object with the given id
     */
    public Answer getById(long id) {
        return mDaoSession.getAnswerDao().load(id);
    }

    /**
     * Adds an Answer object to the database
     *
     * @param answer answer to be created in the answer table
     * @return id of the created object
     */
    public long create(Answer answer) {
        return mDaoSession.getAnswerDao().insert(answer);
    }
}