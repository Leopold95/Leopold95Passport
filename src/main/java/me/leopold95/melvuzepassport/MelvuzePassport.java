package me.leopold95.melvuzepassport;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.leopold95.melvuzepassport.core.Config;
import me.leopold95.melvuzepassport.core.Keys;
import me.leopold95.melvuzepassport.listeners.PlayerJoin;
import me.leopold95.melvuzepassport.listeners.PlayerMove;
import org.bukkit.plugin.java.JavaPlugin;

public final class MelvuzePassport extends JavaPlugin {

    private RegionContainer regions;

    public Keys keys;

    @Override
    public void onEnable() {
        Config.register(this);

        regions = WorldGuard.getInstance().getPlatform().getRegionContainer();
        keys = new Keys(this);

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
