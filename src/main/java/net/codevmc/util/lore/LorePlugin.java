package net.codevmc.util.lore;

import org.bukkit.plugin.java.JavaPlugin;

public class LorePlugin extends JavaPlugin {

    public void onEnable(){
        new ItemLoreHandler(this);
    }

}
