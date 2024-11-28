package com.rankup.empire.currencies.commands;

import com.rankup.empire.currencies.CurrenciesAPI;
import com.rankup.empire.currencies.CurrenciesPlugin;
import com.rankup.empire.currencies.feat.utils.text.Formats;
import com.rankup.empire.currencies.view.RankingView;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MoneyCommand {

    private final CurrenciesPlugin plugin;

    @Command(
            name = "money",
            aliases = {"coins"}
    )

    public void execute(Context<CommandSender> context) {
        val player = (Player) context.getSender();
        val user = plugin.getUserCache().getByID(player.getUniqueId());

        player.sendMessage("§aSaldo: §2$§f" + Formats.format(user.getCoins()));
    }

    @Command(
            name = "money.add",
            permission = "currencies.add",
            usage = "money add (jogador) (valor)"
    )

    public void add(Context<CommandSender> context, Player target, String value) {
        val player = (Player) context.getSender();
        val user = plugin.getUserCache().getByID(target.getUniqueId());
        val parse = Formats.parse(value);

        if (user == null) {
            player.sendMessage("§cEste jogador não está online!");
            return;
        }

        CurrenciesAPI.add(target, parse);
        player.sendMessage("§aVocê adicionou " + Formats.format(parse) + " ao jogador " + target.getName() + ".");
    }

    @Command(
            name = "money.pay",
            usage = "money pay (jogador) (valor)"
    )

    public void pay(Context<CommandSender> context, Player target, String value) {
        val player = (Player) context.getSender();
        val parse = Formats.parse(value);
        val user = plugin.getUserCache().getByID(player.getUniqueId());

        if (player == target)
            return;

        if (parse < 1)
            return;

        if (user.getCoins() < parse)
            return;

        if (target == null)
            return;

        CurrenciesAPI.pay(player, target, parse);
        player.sendMessage("§aVocê enviou " + Formats.format(parse) + " para " + target.getName() + ".");

        target.sendMessage("§aVocê recebeu " + Formats.format(parse) + " de " + player.getName() + ".");
    }

    @Command(
            name = "money.ranking",
            aliases = {"top"}
    )

    public void ranking(Context<CommandSender> context) {
        val player = (Player) context.getSender();

        val rankingView = new RankingView(plugin);
        rankingView.open(player);
    }
    @Command(
            name = "money.set",
            usage = "money set (jogador) (valor)",
            permission = "currencies.set"
    )

    public void set(Context<CommandSender> context, Player target, String value) {
        val player = (Player) context.getSender();
        val parse = Formats.parse(value);

        CurrenciesAPI.set(target, parse);
        player.sendMessage("§aVocê setou o money do " + target.getName() + " para " + Formats.format(parse) + ".");
    }
}

