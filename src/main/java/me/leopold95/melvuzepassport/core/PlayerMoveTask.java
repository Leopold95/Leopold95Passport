package me.leopold95.melvuzepassport.core;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.leopold95.melvuzepassport.MelvuzePassport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerMoveTask {
    private final Set<UUID> playerInRegions;

    public PlayerMoveTask(MelvuzePassport plugin, Set<String> regionNames) {
        this.playerInRegions = new HashSet<>();

        // Schedule a task to periodically check player regions
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    Location location = player.getLocation();
                    RegionManager regions = plugin.regions.get(BukkitAdapter.adapt(player.getWorld()));
                    if (regions == null) {
                        return;
                    }

                    RegionQuery query = plugin.regions.createQuery();
                    ApplicableRegionSet toRegions = query.getApplicableRegions(BukkitAdapter.adapt(location));
                    boolean isInAnyRegion = toRegions.getRegions().stream()
                            .anyMatch(region -> regionNames.contains(region.getId().toLowerCase()));

                    if (isInAnyRegion) {
                        playerInRegions.add(player.getUniqueId());
                    } else if (playerInRegions.remove(player.getUniqueId())) {
                        player.sendMessage("You have left a monitored region.");
                    }
                });
            }
        }.runTaskTimer(plugin, 0L, 20L); // Run every second (20 ticks)
    }
}
