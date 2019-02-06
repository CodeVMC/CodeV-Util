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

    public static UUID addUUID(ItemStack stack){
        NBTCompound compound = nbtManager.read(stack);
        if(compound==null)
            compound = new NBTCompound();
        if(compound.containsKey(UUID_KEY))
            return UUID.fromString(compound.getString(UUID_KEY));
        UUID uuid = UUID.randomUUID();
        compound.put(UUID_KEY, SerializationHelper.serialize(uuid.toString()));
        return uuid;
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
