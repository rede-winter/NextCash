package com.nextplugins.cash.ranking;

import com.nextplugins.cash.NextCash;
import lombok.Getter;

@Getter
public class RankingBootstrap {

    private final NextCash plugin;
    private final RankingLocations locations;
    private final RankingRunnable rankingRunnable;

    public RankingBootstrap(NextCash plugin) {
        this.plugin = plugin;

        this.locations = new RankingLocations(plugin);
        this.rankingRunnable = new RankingRunnable(this);
        this.rankingRunnable.register();
    }

    public void shutdown() {
        rankingRunnable.destroy();
    }
}
