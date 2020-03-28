package com.github.gobotan.kagawaprefblock.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Maincommand implements CommandAPI, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
        Player p = (Player) sender;
        p.sendMessage("version:0.4.0");
        p.sendMessage("creater:ganma");
        return false;
    }
}
