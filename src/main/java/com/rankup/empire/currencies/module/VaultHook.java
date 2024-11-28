package com.rankup.empire.currencies.module;

import com.rankup.empire.currencies.feat.utils.text.Formats;
import lombok.RequiredArgsConstructor;
import lombok.val;
import com.rankup.empire.currencies.CurrenciesPlugin;
import com.rankup.empire.currencies.module.wrapper.EconomyWrapper;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;


@RequiredArgsConstructor
public class VaultHook extends EconomyWrapper {

    private static final CurrenciesPlugin plugin = CurrenciesPlugin.getInstance();


    @Override
    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public String getName() {
        return "currencies";
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return Formats.format(v);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {

        val user = plugin.getUserCache().getByID(offlinePlayer.getUniqueId());

        return user != null;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        val user = plugin.getUserFactory().getOrCreate(offlinePlayer.getPlayer());
        if (!plugin.getUserCache().getCachedElements().contains(user)) {
            plugin.getUserCache().addCachedElement(user);
            plugin.getUserStorage().update(user);
        }
        return user.getCoins();
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        if (v > 0) {
            val user = plugin.getUserFactory().getOrCreate(offlinePlayer.getPlayer());
            if (!plugin.getUserCache().getCachedElements().contains(user)) {
                plugin.getUserCache().addCachedElement(user);
                plugin.getUserStorage().update(user);
            }
            if (user.getCoins() >= v) {
                user.withdraw(v);
            }
            return new EconomyResponse(0.0, user.getCoins(), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Valor negativo");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        if (v > 0.0) {
            val user = plugin.getUserFactory().getOrCreate(offlinePlayer.getPlayer());
            if (!plugin.getUserCache().getCachedElements().contains(user)) {
                plugin.getUserCache().addCachedElement(user);
                plugin.getUserStorage().update(user);
            }
            user.deposit(v);

            return new EconomyResponse(0.0, user.getCoins(), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Valor negativo");
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }
}