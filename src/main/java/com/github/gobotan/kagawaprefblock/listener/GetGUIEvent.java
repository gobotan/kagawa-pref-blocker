package com.github.gobotan.kagawaprefblock.listener;

import com.github.gobotan.kagawaprefblock.API.FileAPI;
import com.github.gobotan.kagawaprefblock.API.MinecraftAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class GetGUIEvent implements Listener {

    private Plugin unko;
    private HashSet<Player> isopen = new HashSet<>();

    public GetGUIEvent(Plugin pl){
        Bukkit.getPluginManager().registerEvents(this,pl);
        unko = pl;
    }

    @EventHandler
    public void getclickEvent(InventoryClickEvent e) throws IOException {
        Player pl = (Player)e.getWhoClicked();
        ItemStack clicedItem = e.getCurrentItem();
        Inventory clickedInventory = e.getInventory();
        if(clicedItem == null){
            return;
        }

        if(!clicedItem.hasItemMeta()){
            return;
        }

        if(clickedInventory.getType() != InventoryType.CREATIVE && e.getView().getTitle().contains("あなたは香川県民ですか？")){
            if(clicedItem.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                e.setCancelled(true);
                isopen.add(pl);
                HashSet<UUID> list = FileAPI.getKagawalist();
                list.add(pl.getUniqueId());
                FileAPI.createKagawaList(list);
                HashSet<UUID> list2 = FileAPI.getLoginlist();
                list.add(pl.getUniqueId());
                FileAPI.createLoginList(list2);
                System.out.println("デバッグ：ログインリスト更新");
                pl.closeInventory();
                MinecraftAPI.starttimer(pl);
                return;
            }
            if(clicedItem.getType() == Material.RED_STAINED_GLASS_PANE){
                e.setCancelled(true);
                isopen.add(pl);
                pl.closeInventory();
                HashSet<UUID> list = FileAPI.getLoginlist();
                list.add(pl.getUniqueId());
                FileAPI.createLoginList(list);
                System.out.println("デバッグ：ログインリスト更新");
                return;
            }
            if (clicedItem.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void getInventorycloseEvent(InventoryCloseEvent e){
        if(e.getView().getTitle().contains("あなたは香川県民ですか？") && !isopen.contains((Player) e.getPlayer())){
            new BukkitRunnable(){
                @Override
                public void run() {
                    e.getPlayer().openInventory(MinecraftAPI.getInventory());
                }
            }.runTaskLater(unko,10);

        }
    }
}
