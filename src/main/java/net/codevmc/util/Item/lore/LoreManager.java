package net.codevmc.util.Item.lore;

import net.codevmc.util.Item.ItemUUID;
import net.codevmc.util.Item.lore.util.ItemLoreHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoreManager {

    private ConcurrentHashMap<UUID, Lore> bindMap = new ConcurrentHashMap<>();

    private static LoreManager INSTANCE;

    public static long ASYNC_UPDATE_RATE = 1;

    public LoreManager(JavaPlugin plugin) {
        if (INSTANCE == null)
            INSTANCE = this;
        else return;
        SyncUpdate update =new SyncUpdate();
        update.runTaskTimer(plugin, 1, 1);
        new FlushLore(update).runTaskTimer(plugin,1,1);
        new Thread(new AsyncUpdate()).start();
    }

    public static LoreManager getInstance() {
        return INSTANCE;
    }

    public void bindLore(ItemStack item, Lore lore) {
        bindMap.put(ItemUUID.getUUID(item).orElseGet(() -> ItemUUID.addUUID(item)), lore);
    }

    public Lore getBindLore(ItemStack item) {
        return bindMap.get(ItemUUID.getUUID(item).get());
    }

    private class FlushLore extends BukkitRunnable{

        private final SyncUpdate update;

        public FlushLore(SyncUpdate update){
            this.update = update;
        }

        @Override
        public void run() {
            for(Player p : Bukkit.getOnlinePlayers()){
                if(watchingInventory(p)){
                    p.getInventory().forEach(this::updateItemIfCan);
                    InventoryView view = p.getOpenInventory();
                    if(view.getTopInventory()!=null)
                        view.getTopInventory().forEach(this::updateItemIfCan);
                }
            }
        }

        private boolean watchingInventory(Player p){
            return false;
        }

        private void updateItemIfCan(ItemStack stack){
            ItemUUID.getUUID(stack)
                    .ifPresent(uuid->{
                        if(update.needFlushLore.remove(uuid)){
                            ItemLoreHelper.setLore(stack,update.cacheLore.get(uuid));
                        }
                    });
        }
    }

    private class SyncUpdate extends BukkitRunnable {

        public HashMap<UUID,List<String>> cacheLore = new HashMap<>();

        public Set<UUID> needFlushLore = new TreeSet<>();

        @Override
        public void run() {
            Set<Map.Entry<UUID,Lore>> entries = bindMap.entrySet();
            for(Map.Entry<UUID,Lore> entry:entries){
                UUID itemUUID = entry.getKey();
                Lore lore = entry.getValue();
                lore.update();
                List<String> nowLore = lore.get();
                List<String> beforeLore = cacheLore.get(itemUUID);
                if(beforeLore==null){
                    cacheLore.put(itemUUID,nowLore);
                    continue;
                }
                if(beforeLore.equals(nowLore))
                    continue;
                cacheLore.put(itemUUID,nowLore);
                needFlushLore.add(itemUUID);
            }
        }
    }

    private class AsyncUpdate implements Runnable {

        @Override
        public void run() {
            while (true) {
                for (Lore lore : bindMap.values())
                    lore.asyncUpdate();
                try {
                    Thread.sleep(ASYNC_UPDATE_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
