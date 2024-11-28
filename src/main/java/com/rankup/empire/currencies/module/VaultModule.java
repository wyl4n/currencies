package com.rankup.empire.currencies.module;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultModule {

    @Getter
    private static Economy economy;

    private static void setupEconomy() {
        final RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            VaultModule.economy = economyProvider.getProvider();
        }
    }

    public static void setupVault() {
        setupEconomy();
    }

    static {
        VaultModule.economy = null;
    }
}
