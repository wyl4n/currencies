package com.rankup.empire.currencies.feat.plugin;

import com.rankup.empire.currencies.CurrenciesPlugin;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewFrame;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class CustomPlugin extends JavaPlugin {

    private ViewFrame viewFrame;
    private CurrenciesPlugin plugin;

    public void registerListener(Listener... listeners) {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    public void registerCommands(Object... objects) {
        final BukkitFrame frame = new BukkitFrame(this);
        final MessageHolder messageHolder = frame.getMessageHolder();

        messageHolder.setMessage(MessageType.ERROR, "§cOcorreu um erro ao executar este comando.");
        messageHolder.setMessage(MessageType.INCORRECT_TARGET, "§cEste comando não está disponível para '{target}'.");
        messageHolder.setMessage(MessageType.INCORRECT_USAGE, "§cUso do /{usage}: \n * §c{usage}");
        messageHolder.setMessage(MessageType.NO_PERMISSION, " §cVocê não tem permissão para executar este comando.");
        frame.registerCommands(objects);
    }

    public void registerViews(View... views) {
        this.viewFrame = new ViewFrame(plugin);

        viewFrame.register(views);
    }
}
