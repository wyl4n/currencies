package com.rankup.empire.currencies.feat.data.ranking.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.rankup.empire.currencies.CurrenciesPlugin;
import com.rankup.empire.currencies.feat.data.ranking.Ranking;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class RankingTask implements Runnable {
    private final CurrenciesPlugin plugin;

    @Override
    public void run() {
          List<Ranking> userList = plugin.getUserStorage().findAll().stream()
                .sorted(((o1, o2) -> Double.compare(o2.getCoins(), o1.getCoins())))
                .collect(Collectors.toList());


        plugin.getRankingCache().update(userList);

    }
}
