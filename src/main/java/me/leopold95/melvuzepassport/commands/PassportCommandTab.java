package me.leopold95.melvuzepassport.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PassportCommandTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1)
            return Arrays.asList(SubCommands.GIVE);

        if(strings.length == 2)
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());

        return Collections.emptyList();
    }
}
