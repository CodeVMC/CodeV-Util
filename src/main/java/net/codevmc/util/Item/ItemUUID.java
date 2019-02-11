package net.codevmc.util.Item;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import net.codevmc.util.nbt.NBTHelper;
import net.codevmc.util.serialization.SerializationHelper;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ItemUUID {

    private static final String UUID_KEY = "ITEM_UUID";

    private static final LoadingCache<ItemStack,UUID> UUID_CACHE = CacheBuilder.newBuilder()
            .initialCapacity(Bukkit.getMaxPlayers()*30)
            .maximumSize(Bukkit.getMaxPlayers()*40)
            .concurrencyLevel(8)
            .expireAfterAccess(2, TimeUnit.HOURS)
            .build(new CacheLoader<ItemStack, UUID>() {
                @Override
                public UUID load(ItemStack stack){
                    return getUUIDFromNBT(stack);
                }
            });

    private static NBTManager nbtManager = NBTManager.getInstance();

    /**
     *add a new uuid to stack.
     * @param stack
     * @return created uuid
     */
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
        try {
            UUID uuid = UUID_CACHE.get(stack);
            if(uuid==null)
                return Optional.empty();
            return Optional.of(uuid);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static UUID getUUIDFromNBT(ItemStack stack){
        NBTCompound compound = NBTHelper.getNBT(stack);
        return UUID.fromString(compound.getString(UUID_KEY));
    }

}
