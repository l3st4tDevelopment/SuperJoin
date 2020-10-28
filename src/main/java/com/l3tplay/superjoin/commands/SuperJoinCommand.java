package com.l3tplay.superjoin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.l3tplay.superjoin.SuperJoin;
import com.l3tplay.superjoin.utils.ColorUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("superjoin")
@CommandPermission("superjoin.admin")
@RequiredArgsConstructor
public class SuperJoinCommand extends BaseCommand {

    private final SuperJoin plugin;

    @Default
    @Description("Show this help page.")
    @HelpCommand
    public void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("setspawn")
    @Description("Set the spawn point.")
    public void onSetSpawn(Player player) {
        if (plugin.getSpawnManager() == null) {
            player.sendMessage(ColorUtils.colorString(plugin.getConfig().getString("messages.featureDisabled")));
            return;
        }

        player.sendMessage(ColorUtils.colorString(plugin.getConfig().getString("messages.spawnSet")));
        plugin.getSpawnManager().setLocation(player.getLocation());
    }

    @Subcommand("reload")
    @Description("Reload the plugin.")
    public void onReload(CommandSender sender) {
        sender.sendMessage(ColorUtils.colorString(plugin.getConfig().getString("messages.reloadedConfig")));

        plugin.reloadConfig();
        plugin.loadFeatures();
    }
}
