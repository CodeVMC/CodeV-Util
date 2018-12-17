package net.codev.util.lore.replacement;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface ReplacementFunction<T extends ItemStack,R extends Map<String,? extends Object>> {
    R get(T t);
}
