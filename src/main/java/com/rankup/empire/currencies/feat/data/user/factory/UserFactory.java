package com.rankup.empire.currencies.feat.data.user.factory;

import lombok.val;
import com.rankup.empire.currencies.CurrenciesPlugin;
import lombok.RequiredArgsConstructor;
import com.rankup.empire.currencies.feat.data.user.User;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class UserFactory {
    private final CurrenciesPlugin plugin;

    public void create(Player player) {
        val userCache = plugin.getUserCache();
        val storage = plugin.getUserStorage();

        val find = storage.find(player.getUniqueId().toString());
        if(find != null) {
            userCache.addCachedElement(find);
            return;
        }

        val user = User.builder()
                .uuid(player.getUniqueId())
                .username(player.getName())
                .coins(0)
                .build();

        storage.insert(user);
        userCache.addCachedElement(user);
    }

    public User getOrCreate(Player player) {
        final User user = plugin.getUserCache().getByID(player.getUniqueId());
        if (user == null) {
            return User
                    .builder()
                    .uuid(player.getUniqueId())
                    .username(player.getName())
                    .coins(0)
                    .build();
        }
        return user;
    }

    public void remove(Player player) {
        val userCache = plugin.getUserCache();
        val storage = plugin.getUserStorage();

        val find = userCache.getByID(player.getUniqueId());
        if(find == null) return;

        storage.update(find);
        userCache.removeCachedElement(find);
    }


}