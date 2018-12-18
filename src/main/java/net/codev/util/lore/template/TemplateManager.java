package net.codev.util.lore.template;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import me.dpohvar.powernbt.nbt.NBTTagByteArray;
import net.codev.util.lore.Lore;
import net.codev.util.nbt.NBTHelper;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class TemplateManager {

    private static final String TEMPLATE_KEY = "lore_template";

    //TODO cache the serialized paragrah
    private static final HashMap<String, Lore> templateLoreCache = new HashMap<>();

    private static NBTManager nbtManager = NBTManager.getInstance();

    public static void setItemTemplate(ItemStack stack, Lore template) {
        NBTCompound stackCompound = NBTHelper.getNBT(stack);
        try {
            NBTTagByteArray bytes = NBTHelper.objectToNBTTagByteArray(template);
            stackCompound.put(TEMPLATE_KEY, bytes);
            nbtManager.write(stack, stackCompound);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Lore> getTemplate(ItemStack stack) {
        NBTCompound compound = NBTHelper.getNBT(stack);
        if (compound.containsKey(TEMPLATE_KEY)) {
            NBTTagByteArray bytes = NBTHelper.getNBTTagByteArray(compound, TEMPLATE_KEY);
            try {
                return Optional.of(NBTHelper.nbtTagByteArrayToObject(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public static boolean hasTemplate(ItemStack stack) {
        NBTCompound compound = NBTHelper.getNBT(stack);
        return compound.containsKey(TEMPLATE_KEY);
    }

}
