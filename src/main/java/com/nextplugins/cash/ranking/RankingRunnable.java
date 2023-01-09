package com.nextplugins.cash.ranking;

import com.nextplugins.cash.NextCash;
import com.nextplugins.cash.storage.RankingStorage;
import org.bukkit.Bukkit;

public class RankingRunnable implements Runnable {

    private final NextCash plugin;
    private final RankingHologram ranking;
    private final RankingStorage rankingStorage;

    public RankingRunnable(RankingBootstrap bootstrap) {
        this.plugin = bootstrap.getPlugin();
        this.ranking = new RankingHologram(bootstrap.getPlugin(), bootstrap.getLocations());
        this.rankingStorage = bootstrap.getPlugin().getRankingStorage();

        rankingStorage.updateRanking(true);
    }

    public void register() {
        final int updateDelay = 10 * 60; // 10 minutes

        Bukkit.getScheduler().runTaskTimer(plugin, this, 20L, updateDelay * 20L);
    }

    @Override
    public void run() {
        update();
    }

    public void update() {
        ranking.update(rankingStorage.getRankingAccounts());
    }

    public void destroy() {
        ranking.clear();
    }
}
