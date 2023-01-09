package com.nextplugins.cash.storage;

import com.nextplugins.cash.NextCash;
import com.nextplugins.cash.api.group.GroupWrapperManager;
import com.nextplugins.cash.api.model.Account;
import com.nextplugins.cash.ranking.RankingChatBody;
import lombok.Data;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
public final class RankingStorage {

    private final List<Account> rankingAccounts = new ArrayList<>();
    private final RankingChatBody rankingChatBody = new RankingChatBody();
    private final GroupWrapperManager groupManager;
    private long nextUpdate;

    public boolean updateRanking(boolean force) {
        if (!force && nextUpdate > System.currentTimeMillis()) return false;

        nextUpdate = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);

        update();

        return true;
    }

    private void update() {
        val plugin = NextCash.getInstance();
        val accounts = plugin.getAccountDAO().selectAll("ORDER BY balance DESC LIMIT 10");

        System.out.println("--------------------------------------");
        for (Account account : accounts) {
            System.out.println(account.getOwner() + " - " + account.getBalance());
        }

        if (!accounts.isEmpty()) {
            rankingAccounts.clear();

            this.rankingAccounts.addAll(accounts);
        }
    }
}
