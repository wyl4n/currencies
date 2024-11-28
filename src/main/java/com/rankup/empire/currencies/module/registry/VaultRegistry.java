package com.rankup.empire.currencies.module.registry;

import lombok.RequiredArgsConstructor;
import com.rankup.empire.currencies.CurrenciesPlugin;
import com.rankup.empire.currencies.module.VaultHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

@RequiredArgsConstructor
public class VaultRegistry {
    private final CurrenciesPlugin plugin;

    public void register() {
        Bukkit.getServer().getServicesManager()
                .register(
                        Economy.class,
                        new VaultHook(),
                        plugin,
                        ServicePriority.Highest
                );

    }
}
