package com.github.gobotan.kagawaprefblock.API;

import com.github.gobotan.kagawaprefblock.Kagawaprefblock;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class FileAPI {
    public static void createKagawaList(HashSet<UUID> playerlist) throws IOException {
        File folder = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data");
        folder.mkdirs();
        File file = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data" + File.separator + "kagawalist.dat");
        ObjectOutputStream os;

        try {
            os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(playerlist);
            os.close();
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.WARNING, "ファイルの作成、または更新に失敗しました。");
        }
    }

    @SuppressWarnings("unchecked")
    public static HashSet<UUID> getKagawalist() throws IOException{
        HashSet<UUID> list = null;
        File folder = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data");
        folder.mkdirs();
        File file = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data" + File.separator + "kagawalist.dat");

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                list = (HashSet<UUID>) is.readObject();
                is.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            Bukkit.getLogger().log(Level.WARNING,"ファイルの読み取りに失敗しました。");
        }

        return list;
    }

    public static void createLoginList(HashSet<UUID> playerlist) throws IOException {
        File folder = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data");
        folder.mkdirs();
        File file = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data" + File.separator + "Loginlist.dat");
        ObjectOutputStream os;

        try {
            os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(playerlist);
            os.close();
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.WARNING, "ファイルの作成、または更新に失敗しました。");
        }
    }

    @SuppressWarnings("unchecked")
    public static HashSet<UUID> getLoginlist() throws IOException{
        HashSet<UUID> list = null;
        File folder = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data");
        folder.mkdirs();
        File file = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data" + File.separator + "Loginlist.dat");

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                list = (HashSet<UUID>) is.readObject();
                is.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            Bukkit.getLogger().log(Level.WARNING,"ファイルの読み取りに失敗しました。");
        }

        return list;
    }

    public static boolean iscreatekagawalist(){
        File file = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data" + File.separator + "kagawalist.dat");
        return file.exists();
    }

    public static boolean iscreateloginlist(){
        File file = new File(Kagawaprefblock.plugin.getDataFolder() + File.separator + "data" + File.separator + "Loginlist.dat");
        return file.exists();
    }
}
