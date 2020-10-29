package com.l3tplay.superjoin.eventaction;

import com.l3tplay.superjoin.SuperJoin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class EventActionListener implements Listener {

    private final SuperJoin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!isEnabled()) {
            return;
        }

        event.setJoinMessage(null);
        plugin.getActionManager().executeActions(player, plugin.getEventActionManager().getGlobalActions().getJoinActions());
        plugin.getEventActionManager().getActions(player)
                .ifPresent(action -> plugin.getActionManager().executeActions(player, action.getJoinActions()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!isEnabled()) {
            return;
        }

        event.setQuitMessage(null);
        plugin.getActionManager().executeActions(player, plugin.getEventActionManager().getGlobalActions().getQuitActions());
        plugin.getEventActionManager().getActions(player)
                .ifPresent(action -> plugin.getActionManager().executeActions(player, action.getQuitActions()));
    }

    private boolean isEnabled() {
        return plugin.getEventActionManager() != null;
    }
}
