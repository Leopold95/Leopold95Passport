package me.leopold95.melvuzepassport.commands;

import me.leopold95.melvuzepassport.MelvuzePassport;
import me.leopold95.melvuzepassport.core.Config;
import me.leopold95.melvuzepassport.core.Items;
import me.leopold95.melvuzepassport.core.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class PassportCommand implements CommandExecutor {
    private MelvuzePassport plugin;
    public PassportCommand(MelvuzePassport plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        boolean consoleAllowed = Config.getBoolean("allow-passport-consol");

        if(!(sender instanceof Player) && !consoleAllowed){
            sender.sendMessage(Config.getMessage("only-for-players"));
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0){
            return false;
        }

        switch (args[0]){
            case SubCommands.GIVE:
                if(!player.hasPermission(Permissions.GIVE)){
                    player.sendMessage(Config.getMessage("permission-bad"));
                    break;
                }

                if(args.length != 2){
                    String message = Config.getMessage("bad-input-give")
                            .replace("%action1%", SubCommands.GIVE)
                            .replace("%player%", "player");

                    player.sendMessage(message);
                    break;
                }

                if(!hasEmptySlots(player)){
                    player.sendMessage(Config.getMessage("inventory-full"));
                    break;
                }

                if(!canUse(player)){
                    player.sendMessage(Config.getMessage("passport-give-bad-delay").
                            replace("%globaldelay%", String.valueOf(Config.getInt("passport-give-delay-seconds"))));
                    break;
                }

                player.getInventory().addItem(plugin.passportItem.clone());

                player.sendMessage(Config.getMessage("passport-gotten"));

                break;
        }

        return true;
    }

    private boolean hasEmptySlots(Player player) {
        return player.getInventory().firstEmpty() != -1;
    }

    private boolean canUse(Player player){
        if(!player.getPersistentDataContainer().has(plugin.keys.PASSPORT_COMMAND_DELAY, PersistentDataType.STRING)){
            player.getPersistentDataContainer().set(plugin.keys.PASSPORT_COMMAND_DELAY, PersistentDataType.STRING, LocalDateTime.now().toString());
            return true;
        }

        String lastUsage = player.getPersistentDataContainer().get(plugin.keys.PASSPORT_COMMAND_DELAY, PersistentDataType.STRING);
        LocalDateTime savedDateTime = LocalDateTime.parse(lastUsage);
        LocalDateTime currentDateTime = LocalDateTime.now();

        long secondsElapsed = ChronoUnit.SECONDS.between(savedDateTime, currentDateTime);

        if (secondsElapsed > Config.getInt("passport-give-delay-seconds")) {
            player.getPersistentDataContainer().set(plugin.keys.PASSPORT_COMMAND_DELAY, PersistentDataType.STRING, currentDateTime.toString());
            return true;
        } else {
            return false;
        }
    }
}


