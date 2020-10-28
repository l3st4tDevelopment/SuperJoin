package com.l3tplay.superjoin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.l3tplay.superjoin.SuperJoin;
import com.l3tplay.superjoin.utils.ColorUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@CommandAlias("spawn")
@RequiredArgsConstructor
public class SpawnCommand extends BaseCommand {

    private final SuperJoin plugin;

    @Default
    public void onSpawn(Player player) {
        if (plugin.getSpawnManager() == null) {
            player.sendMessage(ColorUtils.colorString(plugin.getConfig().getString("messages.featureDisabled")));
            return;
        }

        plugin.getSpawnManager().getLocation().ifPresent(location -> player.teleport(location));
        player.sendMessage(ColorUtils.colorString(plugin.getConfig().getString("messages.teleportedToSpawn")));
    }
}
