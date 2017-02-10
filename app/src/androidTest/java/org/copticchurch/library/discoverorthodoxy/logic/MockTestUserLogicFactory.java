package org.copticchurch.library.discoverorthodoxy.logic;

import org.copticchurch.library.discoverorthodoxy.model.User;
import org.mockito.Mockito;

/**
 * Created by funkv on 07.03.2016.
 */
public class MockTestUserLogicFactory extends UserLogicFactory {
    @Override
    public DueChallengeLogic createDueChallengeLogic(User user) {
        return Mockito.mock(DueChallengeLogic.class);
    }
}
