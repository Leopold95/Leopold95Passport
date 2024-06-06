package me.leopold95.melvuzepassport;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.leopold95.melvuzepassport.core.Config;
import me.leopold95.melvuzepassport.core.Keys;
import me.leopold95.melvuzepassport.core.PlayerMoveTask;
import me.leopold95.melvuzepassport.listeners.PlayerJoin;
import me.leopold95.melvuzepassport.listeners.PlayerMove;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class MelvuzePassport extends JavaPlugin {

    public RegionContainer regions;
    public WorldGuard wg;
    public Keys keys;
    public List<String> preventRegions;

    @Override
    public void onEnable() {
        Config.register(this);

        //preventRegions = Config.getStringList("prevent-leaving-regions");

        wg = WorldGuard.getInstance();
        regions = WorldGuard.getInstance().getPlatform().getRegionContainer();
        keys = new Keys(this);

        new PlayerMoveTask(this, Config.getStringSet("prevent-leaving-regions"));

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
