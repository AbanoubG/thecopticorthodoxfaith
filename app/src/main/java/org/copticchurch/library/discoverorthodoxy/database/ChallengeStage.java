package org.copticchurch.library.discoverorthodoxy.database;

/**
 * Created by Christian Kost
 *
 * Defines common functions for stages
 */
public class ChallengeStage {
    private static int[] stageColorResources = new int[] {
        -1,
        org.copticchurch.library.discoverorthodoxy.R.color.colorStage1,
        org.copticchurch.library.discoverorthodoxy.R.color.colorStage2,
        org.copticchurch.library.discoverorthodoxy.R.color.colorStage3,
        org.copticchurch.library.discoverorthodoxy.R.color.colorStage4,
        org.copticchurch.library.discoverorthodoxy.R.color.colorStage5,
        org.copticchurch.library.discoverorthodoxy.R.color.colorStage6
    };

    /**
     * Returns the resource id of the color for this stage
     * @param stage challenge type to get the color for
     * @return resource id corresponding to the stage's associated color
     */
    public static int getColorResource(int stage) {
        return stageColorResources[stage];
    }
}
