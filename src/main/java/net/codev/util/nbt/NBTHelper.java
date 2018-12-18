package net.codev.util.nbt;

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

}
