package net.codev.util.lore.util;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTList;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemLoreHelper {

    public static void setLore(ItemStack stack, List<String> list){
        if(list.isEmpty())
            return;
        NBTList nbtList = new NBTList(list);
        setNBTLore(nbtList,stack);
    }

    private static void setNBTLore(NBTList list,ItemStack stack){
        NBTCompound stackCompound = NBTHelper.getNBT(stack);
        NBTCompound displayCompound = getDisplay(stackCompound);
        displayCompound.bind("Lore",list);
        stackCompound.bind("display",displayCompound);
        NBTManager.getInstance().write(stack,stackCompound);
    }

    private static NBTCompound getDisplay(NBTCompound nbtCompound){
        if(nbtCompound.containsKey("display"))
            return nbtCompound.getCompound("display");
        return new NBTCompound();
    }


}
