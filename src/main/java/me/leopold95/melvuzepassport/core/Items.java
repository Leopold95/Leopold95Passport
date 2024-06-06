package me.leopold95.melvuzepassport.core;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Items {
    public static ItemStack createPassport(NamespacedKey key){
        ItemStack passport = new ItemStack(Material.REDSTONE_LAMP);
        ItemMeta meta = passport.getItemMeta();

        try {
            passport.setType(Material.valueOf(Config.getString("passport-item-type")));
        }
        catch (Exception exp){
            exp.printStackTrace();
        }

        meta.setDisplayName(Config.getString("passport-item-name"));
        meta.setLore(Config.getStringList("passport-item-lore"));
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);

        passport.setItemMeta(meta);
        return passport;
    }
}
