package com.github.gobotan.kagawaprefblock;

import com.github.gobotan.kagawaprefblock.API.FileAPI;
import com.github.gobotan.kagawaprefblock.command.CommandHander;
import com.github.gobotan.kagawaprefblock.command.Maincommand;
import com.github.gobotan.kagawaprefblock.command.subcommands.Kbset;
import com.github.gobotan.kagawaprefblock.command.subcommands.Kbdelete;
import com.github.gobotan.kagawaprefblock.listener.GetGUIEvent;
import com.github.gobotan.kagawaprefblock.listener.GetListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public final class Kagawaprefblock extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        new GetListener(this);
        new GetGUIEvent(this);
        getLogger().info("香川県民ブロッカーが起動しました。");
        registerCommands();
        plugin = this;
        this.getDataFolder().mkdirs();
        if(!FileAPI.iscreatekagawalist()){
            HashSet<UUID> list = new HashSet<>();
            try {
                FileAPI.createKagawaList(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!FileAPI.iscreateloginlist()){
            HashSet<UUID> list = new HashSet<>();
            try {
                FileAPI.createLoginList(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(){
        CommandHander hander = new CommandHander();

        hander.regiseter("kb",new Maincommand());
        hander.regiseter("set",new Kbset());
        hander.regiseter("delete",new Kbdelete());

        getCommand("kb").setExecutor(hander);
    }
}
