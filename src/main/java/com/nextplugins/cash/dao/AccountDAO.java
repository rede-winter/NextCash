package com.nextplugins.cash.dao;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.nextplugins.cash.api.model.Account;
import com.nextplugins.cash.dao.adapter.AccountAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class AccountDAO {

    private final String TABLE = "cash_accounts";

    private final SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" + "owner CHAR(16) NOT NULL PRIMARY KEY,"
                + "balance DOUBLE NOT NULL,"
                + "receive_cash INTEGER(1) NOT NULL DEFAULT 1"
                + ");");
    }

    public Account selectOne(String owner) {
        return sqlExecutor.resultOneQuery(
                "SELECT * FROM " + TABLE + " WHERE owner = ?",
                statement -> statement.set(1, owner),
                AccountAdapter.class);
    }

    public Set<Account> selectAll(String query) {
        return sqlExecutor.resultManyQuery("SELECT * FROM " + TABLE + " " + query, $ -> {}, AccountAdapter.class);
    }

    public void saveOne(Account account) {
        sqlExecutor.updateQuery("REPLACE INTO " + TABLE + " VALUES(?,?,?)", statement -> {
            statement.set(1, account.getOwner());
            statement.set(2, account.getBalance());
            statement.set(3, account.isReceiveCash() ? 1 : 0);
        });
    }

    public void deleteOne(Account account) {
        sqlExecutor.updateQuery("DELETE FROM " + TABLE + " WHERE owner = '" + account.getOwner() + "'");
    }
}
