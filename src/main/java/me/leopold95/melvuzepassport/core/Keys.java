package me.leopold95.melvuzepassport.core;

import me.leopold95.melvuzepassport.MelvuzePassport;
import org.bukkit.NamespacedKey;

import java.security.Key;

public class Keys {
    private MelvuzePassport plugin;

    public final NamespacedKey PASSPORT_ITEM;

    public Keys(MelvuzePassport plugin){
        this.plugin = plugin;
        PASSPORT_ITEM = new NamespacedKey(this.plugin, "PASSPORT_ITEM");
    }

}
