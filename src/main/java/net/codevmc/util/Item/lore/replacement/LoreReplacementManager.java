package net.codevmc.util.Item.lore.replacement;

import java.util.HashMap;
import java.util.Optional;

public class LoreReplacementManager {

    private static LoreReplacementManager INSTANCE = new LoreReplacementManager();

    public static LoreReplacementManager getInstance() {
        return INSTANCE;
    }

    private HashMap<String, ReplacementFunction> functionMap = new HashMap<>();

    public void putFunction(String functionName, ReplacementFunction function) {
        functionMap.put(functionName, function);
    }

    public Optional<ReplacementFunction> getReplacement(String replacementName) {
        return Optional.ofNullable(functionMap.get(replacementName));
    }

}
