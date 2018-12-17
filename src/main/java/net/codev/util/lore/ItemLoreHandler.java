package net.codev.util.lore;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import net.codev.util.lore.replacement.LoreReplacementManager;
import net.codev.util.lore.util.ItemLoreHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ItemLoreHandler {

    private static ItemLoreHandler INSTANCE;

    private static final String REPLACEMENT_LIST_KEY = "replacementList";

    private final JavaPlugin plugin;
    private NBTManager nbtManager = NBTManager.getInstance();
    private PlayerViewInventoryHandler viewHander;

    public ItemLoreHandler(JavaPlugin plugin) {
        INSTANCE = this;
        this.plugin = plugin;
        viewHander = new PlayerViewInventoryHandler();
        Bukkit.getServer().getPluginManager().registerEvents(viewHander,plugin);
        new BukkitRunnable() {

            private final LoreReplacementManager loreReplacementManager = LoreReplacementManager.getInstance();
            @Override
            public void run() {
                Bukkit
                        .getOnlinePlayers()
                        .stream()
                        .filter(viewHander::viewing)
                        .collect(ArrayList<ItemStack>::new,(list,player)->list.addAll(getViewingItem(player)),ArrayList::addAll)
                        .stream()
                        .filter(stack -> stack!=null)
                        .filter(stack -> stack.getType()!= Material.AIR)
                        .forEach(stack->{
                            LoreManager.getTemplateLore(stack)
                                    .ifPresent(lore-> ItemLoreHelper.setLore(stack,lore.get(getMap(stack))));
                        });
            }

            private List<ItemStack> getViewingItem(Player player) {
                List<ItemStack> list = new ArrayList<>();
                InventoryView view = player.getOpenInventory();
                list.addAll(Arrays.asList(view.getBottomInventory().getContents()));
                if (view.getTopInventory() != null)
                    list.addAll(Arrays.asList(view.getTopInventory().getContents()));
                return list;
            }

            private Map<String,Object> getMap(ItemStack stack){
                Set<String> set = getBindList(stack);
                return set
                        .stream()
                        .collect(HashMap::new,(map,replacementName)->map.putAll(loreReplacementManager.getReplacement(replacementName,stack)),
                                HashMap::putAll);
            }
        }.runTaskTimer(plugin, 5, 1);
    }

    public void bind(ItemStack stack, String replacementName) {
        Set<String> list = getBindList(stack);
        list.add(replacementName);
        setBindList(stack, list);
    }

    private Set<String> getBindList(ItemStack stack) {
        NBTCompound stackCompound = nbtManager.read(stack);
        if (!stackCompound.containsKey(REPLACEMENT_LIST_KEY))
            return new TreeSet<>();
        TreeSet<Object> set = new TreeSet();
        stackCompound.getList(REPLACEMENT_LIST_KEY).toCollection(set);
        return (Set<String>) (Object) set;
    }

    private void setBindList(ItemStack stack, Set<String> list) {
        NBTCompound stackCompound = nbtManager.read(stack);
        stackCompound.put(REPLACEMENT_LIST_KEY, list);
    }

    public void unbind(ItemStack stack, String replacementName) {
        Set<String> list = getBindList(stack);
        list.remove(replacementName);
        setBindList(stack, list);
    }

    public Collection<String> getItemBindList(ItemStack stack) {
        return Collections.unmodifiableCollection(getBindList(stack));
    }

    public static ItemLoreHandler getInstance() {
        return INSTANCE;
    }

    private class PlayerViewInventoryHandler implements Listener {


        private HashSet<UUID> viewingSet = new HashSet<>();

        //TODO Whether the player has opened the inventory
        public boolean viewing(Player player) {
            return true;
        }


    }
}
