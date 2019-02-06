package net.codevmc.util.Item;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import net.codevmc.util.serialization.SerializationHelper;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class ItemUUID {

    private static final String UUID_KEY = "ITEM_UUID";

    private static NBTManager nbtManager = NBTManager.getInstance();

    public static void addUUID(ItemStack stack){
        NBTCompound compound = nbtManager.read(stack);
        if(compound==null)
            compound = new NBTCompound();
        compound.put(UUID_KEY, SerializationHelper.serialize(UUID.randomUUID().toString()));
    }

    public static Optional<UUID> getUUID(ItemStack stack){
        NBTCompound compound = nbtManager.read(stack);
        if(compound==null)
            return Optional.empty();
        String uuidString = compound.getString(UUID_KEY);
        if(uuidString==null)
            return Optional.empty();
        return Optional.of(UUID.fromString(uuidString));
    }


}
