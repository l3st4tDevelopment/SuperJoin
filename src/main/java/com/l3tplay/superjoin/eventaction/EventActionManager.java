package com.l3tplay.superjoin.eventaction;

import com.l3tplay.superjoin.SuperJoin;
import com.l3tplay.superjoin.eventaction.EventAction;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class EventActionManager {

    private final FileConfiguration config;
    @Getter private final EventAction globalActions;
    private final SortedMap<Integer, EventAction> actionMap = new TreeMap<>();
    @Getter private final List<String> firstJoinActions;

    public EventActionManager(SuperJoin plugin) {
        File file = new File(plugin.getDataFolder(), "eventActions.yml");
        if (!file.exists()) {
            plugin.saveResource("eventActions.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        this.globalActions = new EventAction(config.getStringList("global.joinActions"), config.getStringList("global.quitActions"), null);
        this.firstJoinActions = config.getStringList("firstJoinActions");

        loadActions();
    }

    private void loadActions() {
        for (String key : config.getConfigurationSection("groups").getKeys(false)) {
            Integer priority = config.getInt("groups." + key + ".priority");
            List<String> joinActions = config.getStringList("groups." + key + ".joinActions");
            List<String> quitActions = config.getStringList("groups." + key + ".quitActions");

            actionMap.put(priority, new EventAction(joinActions, quitActions, "superjoin.groups." + key));
        }
    }

    public Optional<EventAction> getActions(Player player) {
        return actionMap.values().stream().filter(action -> player.hasPermission(action.getPermission())).findFirst();
    }
 }
