package net.codevmc.util.Item.lore.paragraph;

import net.codevmc.util.Item.lore.Lore;
import net.codevmc.util.serialization.Serialization;

import java.util.ArrayList;
import java.util.List;

public class ParagraphLore extends Lore {

    @Serialization
    private List<Paragraph> paragraphList = new ArrayList<>();

    @Override
    public List<String> get() {
        return paragraphList
                .stream()
                .collect(ArrayList::new,(list,p)->list.addAll(p.getParagraph()),ArrayList::addAll);
    }
}
