package net.codev.util.lore;

import net.codev.util.lore.paragrah.ParagraphListLore;
import net.codev.util.lore.paragrah.line.ParagraphLine;
import net.codev.util.lore.paragrah.line.ListParagraphLine;
import net.codev.util.lore.paragrah.paragraph.Paragraph;
import net.codev.util.lore.paragrah.paragraph.TextParagraph;
import net.codev.util.lore.template.TemplateManager;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class LoreManager {

    public static Optional<Lore> getTemplateLore(ItemStack stack){
        return TemplateManager.getTemplate(stack);
    }

    public static void setTemplateLore(ItemStack stack,Lore lore){
        TemplateManager.setItemTemplate(stack,lore);
    }

    public static void bindReplacement(ItemStack stack, String replacementName){
        ItemLoreHandler.getInstance().bind(stack,replacementName);
    }
}
