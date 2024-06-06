package me.leopold95.melvuzepassport.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.leopold95.melvuzepassport.MelvuzePassport;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Set;

public class PlayerMove implements Listener {
    private MelvuzePassport plugin;
    public PlayerMove(MelvuzePassport plugin){
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event){
//        Player player = event.getPlayer();
//        Location to = event.getTo();
//        Location from = event.getFrom();
//
//        if(to == null)
//            return;
//
//        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
//            return;
//        }
//
//        RegionManager regions = plugin.regions.get(BukkitAdapter.adapt(player.getWorld()));
//        if (regions == null) {
//            return;
//        }
//
//        RegionQuery query = plugin.regions.createQuery();
//        ApplicableRegionSet fromRegions = query.getApplicableRegions(BukkitAdapter.adapt(from));
//        ApplicableRegionSet toRegions = query.getApplicableRegions(BukkitAdapter.adapt(to));
//
//        if (!fromRegions.equals(toRegions)) {
//            // Check if the player is leaving any of the specified regions
//            Set<ProtectedRegion> fromRegionSet = fromRegions.getRegions();
//            Set<ProtectedRegion> toRegionSet = toRegions.getRegions();
//
//            boolean wasInAnyRegion = fromRegionSet.stream()
//                    .anyMatch(region -> plugin.preventRegions.contains(region.getId().toLowerCase()));
//            boolean isInAnyRegion = toRegionSet.stream()
//                    .anyMatch(region -> plugin.preventRegions.contains(region.getId().toLowerCase()));
//
//            if (wasInAnyRegion && !isInAnyRegion) {
//                // Player has left one of the specified regions
//                event.getPlayer().sendMessage("You have left a monitored region.");
//            }
//        }
    }
}
