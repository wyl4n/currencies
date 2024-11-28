package com.rankup.empire.currencies.feat.hook;

import com.rankup.empire.currencies.feat.utils.text.Formats;
import lombok.val;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import com.rankup.empire.currencies.CurrenciesPlugin;
import com.rankup.empire.currencies.feat.data.ranking.Ranking;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderHook extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "currencies";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Wylan";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equals("coins")) {
            val user = CurrenciesPlugin.getInstance().getUserCache().getByID(player.getUniqueId());
            if (user == null) return "0";
            return Formats.format(user.getCoins());
        }

        if (params.equalsIgnoreCase("magnata")) {
            List<Ranking> ranking = CurrenciesPlugin.getInstance().getUserStorage().findAll().stream()
                    .sorted(((o1, o2) -> Double.compare(o2.getCoins(), o1.getCoins())))
                    .limit(10L).collect(Collectors.toList());

            val user = ranking.get(0);
            if (user == null || !user.getName().equals(player.getName())) {
                return "";
            }
            return "§2[$]";

        }

        return "Invalid";
    }

}
