package net.codev.util.lore.replacement;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LoreReplacementManager {

    private static LoreReplacementManager INSTANCE = new LoreReplacementManager();

    public static LoreReplacementManager getInstance() {
        return INSTANCE;
    }

    private HashMap<String, ReplacementFunction> functionMap = new HashMap<>();

    public void putFunction(String functionName, ReplacementFunction function) {
        functionMap.put(functionName, function);
    }

    public Map<String, Object> getReplacement(String replacementName, ItemStack stack) {
        if (functionMap.containsKey(replacementName))
            return functionMap.get(replacementName).get(stack);
        return new HashMap<>();
    }

}
