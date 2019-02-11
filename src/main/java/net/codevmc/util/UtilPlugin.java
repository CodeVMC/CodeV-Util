package net.codevmc.util;

import net.codevmc.util.Item.lore.LoreManager;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilPlugin extends JavaPlugin {

    private static UtilPlugin INSTANCE;

    public void onEnable(){

        new LoreManager(this);

    }

    public static UtilPlugin getINSTANCE() {
        return INSTANCE;
    }
}
