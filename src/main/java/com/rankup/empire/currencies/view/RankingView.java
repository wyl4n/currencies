package com.rankup.empire.currencies.view;

import com.rankup.empire.currencies.feat.utils.text.Formats;
import com.rankup.empire.currencies.feat.utils.text.LuckPermUtil;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewItem;
import me.saiintbrisson.minecraft.utils.ItemBuilder;
import com.rankup.empire.currencies.CurrenciesPlugin;
import com.rankup.empire.currencies.feat.data.ranking.Ranking;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class RankingView extends View {

    private final CurrenciesPlugin plugin;

    private static final int[] SLOTS = {
            10, 11, 12, 13, 14, 15, 16, 21, 22, 23
    };

    public RankingView(CurrenciesPlugin plugin) {
        super(4, "Coins ▸ Ranking");
        this.plugin = plugin;

        setCancelOnClick(true);
        setCancelOnPickup(true);
    }

    @Override
    protected void onRender(ViewContext context) {
        final List<Ranking> userList = plugin.getRankingCache().get().stream()
                .sorted(((o1, o2) -> Double.compare(o2.getCoins(), o1.getCoins())))
                .collect(Collectors.toList());

        for (int index = 0; index < 10; index++) {
            if ((index + 1) > userList.size()) continue;

            final ItemStack userItem = userItem((index + 1), userList.get(index));
            context.slot(SLOTS[index]).onRender(
                    $ -> $.setItem(userItem)
            );
        }

        for (int slot : SLOTS) {
            final ViewItem item = context.getItem(slot);
            if (item == null) context.slot(slot).onRender($ -> $.setItem(emptyItem()));
        }
    }

    private ItemStack userItem(int position, Ranking user) {
        return new ItemBuilder(Material.SKULL_ITEM, 1, 3)
                .skull(user.getName())
                .name("§a" + position + "º: " + (user.getName() == null ? "§cNinguém" : LuckPermUtil.getPrefix(Bukkit.getOfflinePlayer(user.getUuid())) + user.getName()))
                .lore("§fCoins: §2$§f" + Formats.format(user.getCoins()))
                .build();
    }

    private ItemStack emptyItem() {
        return new ItemBuilder(Material.SKULL_ITEM, 1, 3)
                .name("§cVazio.")
                .build();
    }
}