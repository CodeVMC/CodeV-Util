package net.codevmc.util.nbt;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.inventory.ItemStack;

public class NBTHelper {

    private static final NBTManager nbtManager;

    static {
        nbtManager = NBTManager.getInstance();
    }

    public static NBTCompound getNBT(ItemStack stack){
        NBTCompound nbtCompound = nbtManager.read(stack);
        if(nbtCompound==null)
            nbtCompound= new NBTCompound();
        return nbtCompound;
    }

    public static void write(ItemStack stack,NBTCompound compound){
        nbtManager.write(stack,compound);
    }

}
