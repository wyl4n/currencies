package com.rankup.empire.currencies.feat.listener;

import lombok.RequiredArgsConstructor;
import lombok.val;
import com.rankup.empire.currencies.CurrenciesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class GeneralListener implements Listener {
    private final CurrenciesPlugin plugin;

    @EventHandler
    public void on(PlayerJoinEvent event) {
        val player = event.getPlayer();
        plugin.getUserFactory().create(player);
    }

    @EventHandler
    public void off(PlayerQuitEvent event) {
        val player = event.getPlayer();
        plugin.getUserFactory().remove(player);
    }

}
