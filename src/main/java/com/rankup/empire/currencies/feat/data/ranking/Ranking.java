package com.rankup.empire.currencies.feat.data.ranking;

import lombok.Builder;
import lombok.Data;
import org.bukkit.Bukkit;

import java.util.UUID;

@Data
@Builder
public class Ranking {

    private final UUID uuid;
    private final String username;
    private final Double coins;

    public String getName() {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }
}
