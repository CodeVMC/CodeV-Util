package net.codevmc.util.Item.lore.replacement;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface ReplacementFunction<T extends ItemStack,R extends Map<String,? extends Object>> {
    R get(Player owner, T t);
}
