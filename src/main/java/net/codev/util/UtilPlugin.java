package net.codev.util;

import net.codev.util.lore.ItemLoreHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilPlugin extends JavaPlugin {

    public void onEnable(){
        new ItemLoreHandler(this);
    }

}
