package com.rankup.empire.currencies;

import com.rankup.empire.currencies.commands.MoneyCommand;
import com.rankup.empire.currencies.feat.plugin.CustomPlugin;
import lombok.Getter;
import com.rankup.empire.currencies.feat.data.ranking.task.RankingTask;
import com.rankup.empire.currencies.feat.data.user.cache.UserCache;
import com.rankup.empire.currencies.feat.data.user.factory.UserFactory;
import com.rankup.empire.currencies.feat.data.user.storage.UserStorage;
import com.rankup.empire.currencies.feat.data.ranking.cache.RankingCache;
import com.rankup.empire.currencies.feat.data.user.task.UserTask;
import com.rankup.empire.currencies.feat.database.MySQLProvider;
import com.rankup.empire.currencies.feat.hook.PlaceholderHook;
import com.rankup.empire.currencies.feat.listener.GeneralListener;
import com.rankup.empire.currencies.module.VaultModule;
import com.rankup.empire.currencies.module.registry.VaultRegistry;
import org.bukkit.Bukkit;

@Getter
public class CurrenciesPlugin extends CustomPlugin {

    private VaultRegistry vaultRegistry;
    private UserCache userCache;
    private UserStorage userStorage;

    private UserFactory userFactory;
    private MySQLProvider mySQLProvider;

    private RankingCache rankingCache;


    @Override
    public void onLoad() {
        mySQLProvider = new MySQLProvider(
                getConfig().getString("mysql.host"),
                getConfig().getInt("mysql.port"),
                getConfig().getString("mysql.database"),
                getConfig().getString("mysql.username"),
                getConfig().getString("mysql.password")
        );
        vaultRegistry = new VaultRegistry(this);
        userCache = new UserCache();
        userFactory = new UserFactory(this);
        userStorage = new UserStorage();
        rankingCache = new RankingCache();
        vaultRegistry.register();

    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        mySQLProvider.init();

        loadRegistry();

    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(userFactory::remove);
        mySQLProvider.closeConnection();

    }

    public void loadRegistry() {

        registerCommands(
                new MoneyCommand(this)
        );

        registerListener(
                new GeneralListener(this)
        );

        VaultModule.setupVault();

        Bukkit.getScheduler().runTaskTimerAsynchronously(
                this,
                new RankingTask(this),
                0L, 8 * 60 * 20L
        );
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                this,
                new UserTask(this),
                0L, 8 * 60 * 20L
        );

        new PlaceholderHook().register();

    }

    public static CurrenciesPlugin getInstance() {
        return getPlugin(CurrenciesPlugin.class);
    }

}
