package com.github.gobotan.kagawaprefblock.command.subcommands;

import com.github.gobotan.kagawaprefblock.API.FileAPI;
import com.github.gobotan.kagawaprefblock.API.MinecraftAPI;
import com.github.gobotan.kagawaprefblock.command.CommandAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class Kbset implements CommandAPI {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
        Player p =  (Player)sender;
        Player target = Bukkit.getPlayerExact(args[1]);
        if(target == null){
            p.sendMessage("そのプレイヤーは見つかりませんでした。");
            return false;
        }
        try {
            HashSet<UUID> list = FileAPI.getKagawalist();
            list.add(target.getUniqueId());
            FileAPI.createKagawaList(list);
            p.sendMessage("対象のプレイヤーを香川県民に追加しました。");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MinecraftAPI.starttimer(target);

        return false;
    }
}
