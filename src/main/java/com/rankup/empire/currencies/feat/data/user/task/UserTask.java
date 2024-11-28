package com.rankup.empire.currencies.feat.data.user.task;

import lombok.RequiredArgsConstructor;
import lombok.val;
import com.rankup.empire.currencies.CurrenciesPlugin;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public class UserTask implements Runnable {

        private final CurrenciesPlugin plugin;


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach($ -> {
            val user = plugin.getUserCache().getByID($.getUniqueId());
            plugin.getUserStorage().update(user);
        });
    }
}
