package net.codevmc.util.Item.lore;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class LoreManager {

    private HashMap<ItemStack, Lore> bindMap = new HashMap<>();

    private static LoreManager INSTANCE;

    public LoreManager(JavaPlugin plugin) {
        if (INSTANCE == null)
            INSTANCE = this;
        new SyncUpdate().runTaskTimer(plugin,1,1);
        new Thread(new AsyncUpdate()).start();
    }

    public static LoreManager getInstance(){
        return INSTANCE;
    }

    public void bindLore(ItemStack item, Lore lore) {

    }

    public Lore getBindLore(ItemStack item,Lore lore){
        return null;
    }

    private class SyncUpdate extends BukkitRunnable {
        @Override
        public void run() {

        }
    }

    private class AsyncUpdate implements Runnable {

        @Override
        public void run() {

        }
    }
}
