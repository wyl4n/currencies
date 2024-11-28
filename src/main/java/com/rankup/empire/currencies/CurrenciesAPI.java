package com.rankup.empire.currencies;

import org.bukkit.entity.Player;

public class CurrenciesAPI {

    public static double get(Player player) {
        return CurrenciesPlugin.getInstance().
                getUserCache().
                getByID(player.getUniqueId()).
                getCoins();
    }

    public static void add(Player player, double value) {
        CurrenciesPlugin.getInstance().getUserCache()
                .getByID(player.getUniqueId())
                .setCoins(get(player) + value
                );
    }

    public static void remove(Player player, double value) {
        CurrenciesPlugin.getInstance().getUserCache()
                .getByID(player.getUniqueId())
                .setCoins(get(player) - value);
    }

    public static void set(Player player, double value) {
        CurrenciesPlugin.getInstance().getUserCache()
                .getByID(player.getUniqueId())
                .set(value);

    }


    public static void pay(Player player, Player target, double value) {
        CurrenciesPlugin.getInstance().getUserCache()
                .getByID(player.getUniqueId())
                .setCoins(CurrenciesPlugin.getInstance().getUserCache()
                        .getByID(player.getUniqueId())
                        .getCoins() - value);

        CurrenciesPlugin.getInstance().getUserCache()
                .getByID(target.getUniqueId())
                .setCoins(CurrenciesPlugin.getInstance().getUserCache()
                        .getByID(target.getUniqueId())
                        .getCoins() + value);
    }
}
