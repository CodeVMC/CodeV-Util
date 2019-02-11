package net.codevmc.util;

import org.bukkit.plugin.java.JavaPlugin;

public class UtilPlugin extends JavaPlugin {

    private static UtilPlugin INSTANCE;

    public void onEnable(){

    }

    public static UtilPlugin getINSTANCE() {
        return INSTANCE;
    }
}
