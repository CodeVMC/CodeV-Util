package net.codevmc.util;

import net.codevmc.util.Item.lore.ItemLoreHandler;
import net.codevmc.util.Item.lore.LoreManager;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilPlugin extends JavaPlugin {

    public void onEnable(){

        new LoreManager(this);

        new ItemLoreHandler(this);
    }

}
