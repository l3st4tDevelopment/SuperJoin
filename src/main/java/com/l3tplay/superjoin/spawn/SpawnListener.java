package com.l3tplay.superjoin.spawn;

import com.l3tplay.superjoin.SuperJoin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class SpawnListener implements Listener {

    private final SuperJoin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!isEnabled()) {
            return;
        }

        Player player = event.getPlayer();

        switch (plugin.getSpawnManager().getSpawnTeleportMode()) {
            case ALWAYS: {
                teleport(player);
                break;
            }
            case FIRST_JOIN: {
                if (!player.hasPlayedBefore()) {
                    teleport(player);
                }
                break;
            }
        }
    }

    private void teleport(Player player) {
        plugin.getSpawnManager().getLocation().ifPresent(location -> player.teleport(location));
    }

    private boolean isEnabled() {
        return plugin.getSpawnManager() != null;
    }
}
