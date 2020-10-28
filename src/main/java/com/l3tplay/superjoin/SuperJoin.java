package com.l3tplay.superjoin;

import co.aikar.commands.PaperCommandManager;
import com.l3tplay.actionapi.ActionManager;
import com.l3tplay.superjoin.commands.SpawnCommand;
import com.l3tplay.superjoin.commands.SuperJoinCommand;
import com.l3tplay.superjoin.eventaction.EventActionListener;
import com.l3tplay.superjoin.eventaction.EventActionManager;
import com.l3tplay.superjoin.spawn.SpawnListener;
import com.l3tplay.superjoin.spawn.SpawnManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class SuperJoin extends JavaPlugin {

    @Getter private ActionManager actionManager;
    @Getter private EventActionManager eventActionManager;
    @Getter private SpawnManager spawnManager;


    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (!new File(getDataFolder() + File.separator + "acf-lang.yml").exists()) {
            saveResource("acf-lang.yml", false);
        }

        this.actionManager = new ActionManager(this);
        loadFeatures();

        Bukkit.getPluginManager().registerEvents(new EventActionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SpawnListener(this), this);

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new SuperJoinCommand(this));
        commandManager.registerCommand(new SpawnCommand(this));
        commandManager.enableUnstableAPI("help");

        try {
            commandManager.getLocales().loadYamlLanguageFile("acf-lang.yml", Locale.ENGLISH);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void loadFeatures() {
        if (getConfig().getBoolean("features.eventActions")) {
            this.eventActionManager = new EventActionManager(this);
        }

        if (getConfig().getBoolean("features.spawn")) {
            this.spawnManager = new SpawnManager(this);
        }
    }
}
