package me.leopold95.melvuzepassport.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.leopold95.melvuzepassport.MelvuzePassport;
import me.leopold95.melvuzepassport.core.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerMove implements Listener {
    private MelvuzePassport plugin;

    private final Map<UUID, String> playerRegions;


    private final Set<String> bannedRegionsList = new HashSet<>();

    public PlayerMove(MelvuzePassport plugin){
        this.plugin = plugin;
        this.playerRegions = new HashMap<>();

        bannedRegionsList.add("rg1");
        bannedRegionsList.add("rg2");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        RegionManager regions = plugin.regions.get(BukkitAdapter.adapt(player.getWorld()));
        if (regions == null) return;

        ApplicableRegionSet fromRegions = regions.getApplicableRegions(BukkitAdapter.asBlockVector(from));
        ApplicableRegionSet toRegions = regions.getApplicableRegions(BukkitAdapter.asBlockVector(to));

        boolean inMainRegion = false;
        boolean inSubRegion = false;

        for (ProtectedRegion region : toRegions) {
            if (plugin.disAllowedTo.contains(region.getId())) {
                inMainRegion = true;
            }
            if (plugin.disAllowedFrom.contains(region.getId())) {
                inSubRegion = true;
            }
        }

        Set<ProtectedRegion> fromRegionSet = fromRegions.getRegions();
        Set<ProtectedRegion> toRegionSet = toRegions.getRegions();

        boolean wasInMainRegion = fromRegionSet.stream().anyMatch(rg -> plugin.disAllowedTo.contains(rg.getId()));

        boolean wasInSubRegion = fromRegionSet.stream().anyMatch(rg -> plugin.disAllowedFrom.contains(rg.getId()));
        boolean willInMainRegion = toRegionSet.stream().anyMatch(rg -> plugin.disAllowedTo.contains(rg.getId()));

        //block leaving subregions to main regions
        if(wasInSubRegion && willInMainRegion && !inSubRegion){
            if(!checkPassport(player))
                event.setCancelled(true);
        }
        else if(wasInMainRegion && !inMainRegion){
            if(!checkPassport(player))
                event.setCancelled(true);
        }
    }

    private boolean checkPassport(Player player){
        return player.getInventory().contains(plugin.passportItem);
    }
}

//        if (!fromRegions.equals(toRegions)) {
//            // Check if the player is leaving any of the specified regions
//            Set<ProtectedRegion> fromRegionSet = fromRegions.getRegions();
//            Set<ProtectedRegion> toRegionSet = toRegions.getRegions();
//
//            boolean wasInAnyRegion = fromRegionSet.stream()
//                    .anyMatch(region -> plugin.preventRegions.contains(region.getId()));
//            boolean isInAnyRegion = toRegionSet.stream()
//                    .anyMatch(region -> plugin.preventRegions.contains(region.getId()));
//
//            //disable movement from ANY banned to non banned
//            if (wasInAnyRegion && !isInAnyRegion) {
//                // Player has left one of the specified regions
//                player.sendMessage("You can left this region");
//
//            } else if (fromRegionSet.stream().anyMatch(rg -> plugin.disAllowedFrom.contains(rg.getId()))
//                && toRegionSet.stream().anyMatch(rg -> plugin.disAllowedTo.contains(rg.getId()))) {
//                player.sendMessage("u cant move form to");
//            }
//        }
//
//    }

//            if(lt.stream().anyMatch(rg -> plugin.preventRegions.contains(rg)))
//                if(lf.stream().anyMatch(rg -> plugin.preventRegions.contains(rg))){
//                    player.sendMessage("frm ban to ban");
//                    return;
//                }
//
//                player.sendMessage("u cant leave");
//
////            if(lf.stream().anyMatch(rg -> plugin.preventRegions.contains(rg)) &&
////                    lt.stream().noneMatch(rg -> plugin.preventRegions.contains(rg)))
////                player.sendMessage("u cant go into 1");
////            else if (lf.stream().noneMatch(rg -> plugin.preventRegions.contains(rg)) &&
////                    lt.stream().anyMatch(rg -> plugin.preventRegions.contains(rg))) {
////                player.sendMessage("u cant go into 2");
////            }
//
//            // Check if the player is leaving any of the specified regions
////            Set<ProtectedRegion> fromRegionSet = fromRegions.getRegions();
////            Set<ProtectedRegion> toRegionSet = toRegions.getRegions();
//
////            boolean wasInAnyRegion = fromRegionSet.stream()
////                    .anyMatch(region -> plugin.preventRegions.contains(region.getId().toLowerCase()));
////
////            boolean isInAnyRegion = toRegionSet.stream()
////                    .anyMatch(region -> plugin.preventRegions.contains(region.getId().toLowerCase()));
//
//            //boolean isInAnyRegion = plugin.preventRegions.contains(region.getId().toLowerCase());
//
////            if (wasInAnyRegion && !isInAnyRegion) {
////                if(!player.getInventory().contains(plugin.passportItem)){
////                    event.getPlayer().sendMessage(Config.getMessage("prevent-region-left"));
////                    event.setCancelled(true);
////                }
////            }
//
////            boolean wasInPreventRegion = fromRegions.getRegions().stream()
////                    .anyMatch(region -> plugin.preventRegions.contains(region.getId().toLowerCase()));
////
////            boolean isInPreventRegion = toRegions.getRegions().stream()
////                    .anyMatch(region -> plugin.preventRegions.contains(region.getId().toLowerCase()));
////
////            if (wasInPreventRegion && !isInPreventRegion) {
////                // Игрок выходит из запрещенной зоны
////                if (!player.getInventory().contains(plugin.passportItem)) {
////                    event.getPlayer().sendMessage(Config.getMessage("prevent-region-left"));
////                    event.setCancelled(true);
////                }
////            } else if (!wasInPreventRegion && isInPreventRegion) {
////                // Игрок входит в запрещенную зону
////                if (!player.getInventory().contains(plugin.passportItem)) {
////                    event.getPlayer().sendMessage(Config.getMessage("prevent-region-enter")); // Новое сообщение
////                    event.setCancelled(true);
////                }
////            }
//        }

//    private boolean isInBannedRegion(ApplicableRegionSet regions, String regionName) {
//        for (ProtectedRegion region : regions) {
//            if (bannedRegionsList.contains(region.getId())) {
//                return region.getId().equalsIgnoreCase(regionName);
//            }
//        }
//        return false;
//    }




