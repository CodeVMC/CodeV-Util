package net.codevmc.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class UtilPlugin extends JavaPlugin {

    private static UtilPlugin INSTANCE;

    private static ArrayList<Runnable> list = new ArrayList<>();

    public void onDisable(){
        list.forEach(Runnable::run);
    }

    public static void callOnDisable(Runnable runnable){
        list.add(runnable);
    }

    public static UtilPlugin getINSTANCE() {
        return INSTANCE;
    }

}
