package me.leopold95.melvuzepassport;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.leopold95.melvuzepassport.commands.PassportCommand;
import me.leopold95.melvuzepassport.commands.PassportCommandTab;
import me.leopold95.melvuzepassport.core.Config;
import me.leopold95.melvuzepassport.core.Items;
import me.leopold95.melvuzepassport.core.Keys;
import me.leopold95.melvuzepassport.listeners.PlayerMove;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class MelvuzePassport extends JavaPlugin {

    public RegionContainer regions;
    public WorldGuard wg;
    public Keys keys;

    public List<String> preventRegions;
    public List<String> disAllowedFrom;
    public List<String> disAllowedTo;

    public List<String> passportCheckWorlds;
    public ItemStack passportItem;

    @Override
    public void onEnable() {
        Config.register(this);

        keys = new Keys(this);

        preventRegions = Config.getStringList("prevent-leaving-regions");
        disAllowedFrom = Config.getStringList("dis-allow-move.from");
        disAllowedTo = Config.getStringList("dis-allow-move.to");

        passportCheckWorlds = Config.getStringList("allowed-worlds");
        passportItem = Items.createPassport(keys.PASSPORT_ITEM);

        wg = WorldGuard.getInstance();
        regions = WorldGuard.getInstance().getPlatform().getRegionContainer();

        getCommand("passport").setExecutor(new PassportCommand(this));
        getCommand("passport").setTabCompleter(new PassportCommandTab());

        getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
    }
}
