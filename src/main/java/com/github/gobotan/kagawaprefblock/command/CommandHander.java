package com.github.gobotan.kagawaprefblock.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandHander implements CommandExecutor {

    private static HashMap<String, CommandAPI> commands = new HashMap<>();

    public void regiseter(String name, CommandAPI cmd) {
        commands.put(name, cmd);
    }

    public boolean exists(String name) {
        return commands.containsKey(name);
    }

    public CommandAPI getExecutor(String name) {
        return commands.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp()) {
                if (args.length == 0) {
                    getExecutor("kb").onCommand(sender, command, label, args);
                    return true;
                }
                if (args.length > 0) {
                    if (exists(args[0])) {
                        getExecutor(args[0]).onCommand(sender, command, label, args);
                        return true;
                    } else {
                        sender.sendMessage("そのコマンドは存在しません！");
                    }
                }
            }
        }
        else {
            Bukkit.getLogger().info("香川県民ブロッカー関連のコマンドは必ずゲーム内から実行してください。");
        }
        return false;
    }
}