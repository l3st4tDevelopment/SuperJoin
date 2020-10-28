package com.l3tplay.superjoin.spawn;

import com.l3tplay.superjoin.SuperJoin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class SpawnManager {

    private Location location;
    private final File spawnFile;
    private final FileConfiguration spawnConfig;

    @Getter private final SpawnTeleportMode spawnTeleportMode;

    public SpawnManager(SuperJoin plugin) {
        this.spawnFile = new File(plugin.getDataFolder(), "spawn.yml");
        if (!spawnFile.exists()) {
            plugin.saveResource("spawn.yml", false);
        }

        this.spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);
        this.spawnTeleportMode = SpawnTeleportMode.valueOf(spawnConfig.getString("joinTeleportMode").toUpperCase());
        loadSpawn();
    }

    private void loadSpawn() {
        if (spawnConfig.getLocation("spawn") == null) {
            return;
        }

        this.location = spawnConfig.getLocation("spawn");
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }

    public void setLocation(Location location) {
        this.location = location;

        spawnConfig.set("spawn", location);

        try {
            spawnConfig.save(spawnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
