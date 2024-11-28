package com.rankup.empire.currencies.feat.data.user.cache;

import com.rankup.empire.currencies.feat.data.user.User;
import com.rankup.empire.currencies.feat.utils.Cache;

import java.util.UUID;

public class UserCache extends Cache<User> {

    public User getByID(UUID uuid) {
        return getCached($ -> $.getUuid().equals(uuid));
    }
}
