package com.github.gobotan.kagawaprefblock.listener;

import com.github.gobotan.kagawaprefblock.API.FileAPI;
import com.github.gobotan.kagawaprefblock.API.HttpAPI;
import com.github.gobotan.kagawaprefblock.API.MinecraftAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.HashSet;

public class GetListener implements Listener {

    private Plugin pl;
    public static HashSet<Player> banplayer = new HashSet<>();

    public GetListener(Plugin pl) {
        Bukkit.getServer().getPluginManager().registerEvents(this, pl);
        this.pl = pl;
    }

    @EventHandler
    public void getJoinEvent(PlayerJoinEvent e) throws IOException {
        JSONObject jo = null;
        Player p = e.getPlayer();
        String ip = p.getAddress().getHostName();
        System.out.println("デバッグ：1");
        if (!banplayer.contains(p)) {
            System.out.println("デバッグ：2");
            if (!FileAPI.getLoginlist().contains(p.getUniqueId())) {
                if (!FileAPI.getKagawalist().contains(p.getUniqueId())) {
                    System.out.println("デバッグ：3");
                    try {
                        jo = HttpAPI.getWhois(ip);
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }

                    String status = jo.getString("status");
                    if (status.contains("success")) {
                        String regionName = jo.getString("regionName");
                        if (regionName.contains("kagawa")) {
                            p.sendMessage("あなたのIPが香川県にあると推定されました。");
                            p.sendMessage("5秒後に表示されるGUIにて香川県民かどうかを回答してください。");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.openInventory(MinecraftAPI.getInventory());
                                }
                            }.runTaskLater(pl, 100);
                        }
                        else {
                            p.sendMessage("このサーバーに初めて接続していることが確認されました。");
                            p.sendMessage("5秒後に表示されるGUIにて香川県民かどうかを回答してください。");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.openInventory(MinecraftAPI.getInventory());
                                }
                            }.runTaskLater(pl, 100);
                        }
                    } else {
                        Bukkit.getLogger().info("ユーザー名：" + p.getName() + "のIPが確認できませんでした。");
                    }
                } else {
                    MinecraftAPI.starttimer(p);
                }
            }
        } else {
            Calendar cl = Calendar.getInstance();
            if (cl.get(Calendar.AM_PM) == 0 && cl.get(Calendar.HOUR) <= 4) {
                p.kickPlayer("60分以上ログインしたのでkickされました。次にログインできるのは" + (cl.get(Calendar.MONTH) + 1) + "月" + cl.get(Calendar.DAY_OF_MONTH) + "日 午前4時00分です。");
            } else {
                cl.add(Calendar.DAY_OF_MONTH, 1);
                p.kickPlayer("60分以上ログインしたのでkickされました。次にログインできるのは" + (cl.get(Calendar.MONTH) + 1) + "月" + (cl.get(Calendar.DAY_OF_MONTH) + "日 午前4時00分です。"));
            }
        }
    }
}
