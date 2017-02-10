package org.copticchurch.library.discoverorthodoxy.logic;

import org.copticchurch.library.discoverorthodoxy.BuildConfig;
import org.copticchurch.library.discoverorthodoxy.database.SettingsDataSource;
import org.copticchurch.library.discoverorthodoxy.model.Settings;

import java.util.Date;

/**
 * Created by Lars Kahra on 16.03.2016.
 * <p/>
 * Provides functions for Settings logic checking
 */
public class SettingsLogic {
    /**
     * Check whether the given duration is valid for a given stage
     *
     * @param stage        stage to check
     * @param durationMsec duration for this stage
     * @return null if this is a valid value, error string id if it is invalid
     */
    public Integer isTimeValidForStage(Settings settings, int stage, long durationMsec) {
        // Assertion
        if (BuildConfig.DEBUG && (stage < 1 || stage > SettingsDataSource.STAGE_COUNT)) {
            throw new RuntimeException("Invalid stage " + stage);
        }

        int prevStage = stage - 1;
        int nextStage = stage + 1;

        // Cannot be smaller than previous
        if (prevStage >= 1) {
            Date time = SettingsDataSource.getTimeboxByStage(settings, prevStage);
            if (durationMsec <= time.getTime()) {
                return org.copticchurch.library.discoverorthodoxy.R.string.error_less_than_previous;
            }
        }

        // Or bigger than next
        if (nextStage <= SettingsDataSource.STAGE_COUNT) {
            Date time = SettingsDataSource.getTimeboxByStage(settings, nextStage);
            if (durationMsec >= time.getTime()) {
                return org.copticchurch.library.discoverorthodoxy.R.string.error_more_than_next;
            }
        }

        // Or 0
        if (durationMsec == 0) {
            return org.copticchurch.library.discoverorthodoxy.R.string.error_zero_value;
        }

        return null;
    }
}
