package net.codevmc.util;

import net.codevmc.util.lore.ItemLoreHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilPlugin extends JavaPlugin {

    public void onEnable(){
        new ItemLoreHandler(this);
    }

}
