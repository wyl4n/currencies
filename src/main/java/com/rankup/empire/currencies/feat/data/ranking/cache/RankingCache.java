package com.rankup.empire.currencies.feat.data.ranking.cache;

import com.rankup.empire.currencies.feat.data.ranking.Ranking;
import com.rankup.empire.currencies.feat.utils.Cache;

import java.util.List;

public class RankingCache extends Cache<Ranking> {

    public List<Ranking> get() {
        return getCachedElements();
    }

    public void update(List<Ranking> rankings) {
        getCachedElements().clear();
        getCachedElements().addAll(rankings);
    }
}
