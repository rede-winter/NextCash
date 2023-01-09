package com.nextplugins.cash;

import com.google.common.base.Stopwatch;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.nextplugins.cash.api.group.GroupWrapperManager;
import com.nextplugins.cash.command.registry.CommandRegistry;
import com.nextplugins.cash.configuration.registry.ConfigurationRegistry;
import com.nextplugins.cash.dao.AccountDAO;
import com.nextplugins.cash.listener.ListenerRegistry;
import com.nextplugins.cash.placeholder.registry.PlaceholderRegistry;
import com.nextplugins.cash.ranking.RankingBootstrap;
import com.nextplugins.cash.sql.SQLProvider;
import com.nextplugins.cash.storage.AccountStorage;
import com.nextplugins.cash.storage.RankingStorage;
import com.nextplugins.cash.util.PlayerPointsFakeDownloader;
import com.nextplugins.cash.util.text.TextLogger;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
public final class NextCash extends JavaPlugin {

    private final TextLogger textLogger = new TextLogger();
    private final boolean debug = getConfig().getBoolean("plugin.debug");

    private SQLConnector sqlConnector;
    private SQLExecutor sqlExecutor;

    private AccountDAO accountDAO;
    private AccountStorage accountStorage;
    private RankingStorage rankingStorage;

    private RankingBootstrap rankingBootstrap;
    private GroupWrapperManager groupWrapperManager;

    public static NextCash getInstance() {
        return getPlugin(NextCash.class);
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        getLogger().info("Iniciando carregamento do plugin.");

        val loadTime = Stopwatch.createStarted();

        sqlConnector = SQLProvider.of(this).setup();
        sqlExecutor = new SQLExecutor(sqlConnector);

        accountDAO = new AccountDAO(sqlExecutor);
        accountStorage = new AccountStorage(accountDAO);
        groupWrapperManager = new GroupWrapperManager();
        rankingStorage = new RankingStorage(groupWrapperManager);

        accountStorage.init();

        rankingBootstrap = new RankingBootstrap(this);

        InventoryManager.enable(this);

        ConfigurationRegistry.of(this).register();
        ListenerRegistry.of(this).register();
        CommandRegistry.of(this).register();
        PlayerPointsFakeDownloader.of(this).download();

        Bukkit.getScheduler()
                .runTaskLater(
                        this,
                        () -> {
                            PlaceholderRegistry.of(this).register();
                            groupWrapperManager.init();
                            rankingStorage.updateRanking(true);
                        },
                        3 * 20L);

        loadTime.stop();
        getLogger().log(Level.INFO, "Plugin inicializado com sucesso. ({0})", loadTime);
    }

    @Override
    public void onDisable() {
        val unloadTiming = Stopwatch.createStarted();

        textLogger.info("Descarregando módulos do plugin... (0/2)");

        unloadRanking();

        accountStorage.getCache().values().forEach(accountStorage.getAccountDAO()::saveOne);
        textLogger.info("Informações das contas foram salvas. (2/2)");

        unloadTiming.stop();

        textLogger.info(String.format("O plugin foi encerrado com sucesso. (%s)", unloadTiming));
    }

    private void unloadRanking() {
        rankingBootstrap.shutdown();

        textLogger.info("NPCs e hologramas foram salvos e descarregados. (1/2)");
    }
}
