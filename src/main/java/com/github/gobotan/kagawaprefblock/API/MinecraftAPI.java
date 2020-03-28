package com.github.gobotan.kagawaprefblock.API;

import com.github.gobotan.kagawaprefblock.Kagawaprefblock;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;


public class MinecraftAPI {

    private static HashMap<UUID, Integer> playtime = new HashMap<>();

    public static BaseComponent[] hovertextbuilder(String text, String hoverText, String command) {
        HoverEvent hoverEvent = null;
        if (hoverText != null) {
            BaseComponent[] hover = new ComponentBuilder(hoverText).create();
            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover);
        }

        ClickEvent clickEvent = null;
        if (command != null) {
            clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, command);
        }

        return new ComponentBuilder(text).event(hoverEvent).event(clickEvent).create();
    }

    public static Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, "あなたは香川県民ですか？");

        ItemStack is = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("あなたは香川県民ですか？");
        is.setItemMeta(im);

        ItemStack is1 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta im1 = is.getItemMeta();
        im1.setDisplayName("はい");
        is1.setItemMeta(im1);

        ItemStack is2 = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta im2 = is.getItemMeta();
        im2.setDisplayName("いいえ");
        is2.setItemMeta(im2);


        for (int a = 0; a <= 26; a++) {
            if (a == 10) {
                inv.setItem(a, is1);
            } else if (a == 16) {
                inv.setItem(a, is2);
            } else {
                inv.setItem(a, is);
            }
        }

        return inv;
    }

    public static void starttimer(Player p) {
        playtime.putIfAbsent(p.getUniqueId(), 3600);
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (FileAPI.getKagawalist().contains(p.getUniqueId())) {
                        playtime.put(p.getUniqueId(), playtime.get(p.getUniqueId()) - 1);
                        TextComponent tc = new TextComponent();
                        String a = playtime.get(p.getUniqueId()) % 3600 / 60 < 10 ? "0" + playtime.get(p.getUniqueId()) % 3600 / 60 : String.valueOf(playtime.get(p.getUniqueId()) % 3600 / 60);
                        String b = playtime.get(p.getUniqueId()) % 60 < 10 ? "0" + playtime.get(p.getUniqueId()) % 60 : String.valueOf(playtime.get(p.getUniqueId()) % 60);
                        tc.setText(a + "：" + b);
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, tc);
                        if (playtime.get(p.getUniqueId()) <= 0) {
                            Calendar cl = Calendar.getInstance();
                            if (cl.get(Calendar.AM_PM) == 0 && cl.get(Calendar.HOUR) <= 4) {
                                p.kickPlayer("60分以上ログインしたのでkickされました。次にログインできるのは" + (cl.get(Calendar.MONTH) + 1) + "月" + cl.get(Calendar.DAY_OF_MONTH) + "日 午前4時00分です。");
                            } else {
                                cl.add(Calendar.DAY_OF_MONTH, 1);
                                p.kickPlayer("60分以上ログインしたのでkickされました。次にログインできるのは" + (cl.get(Calendar.MONTH) + 1) + "月" + (cl.get(Calendar.DAY_OF_MONTH) + 1) + "日 午前4時00分です。");
                            }
                        }
                    }
                    else {
                        this.cancel();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!p.isOnline()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Kagawaprefblock.plugin, 20, 20);
    }
}
