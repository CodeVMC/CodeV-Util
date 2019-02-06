package net.codevmc.util.Item.lore.paragraph.paragraph;

import net.codevmc.util.Item.lore.paragraph.Paragraph;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Map;

public class PlaceholderParagraph implements Paragraph {

    private String placeholder;

    public PlaceholderParagraph(String s){
        placeholder =s;
    }

    @Override
    public String getParagraph(Map<String,? extends Object> map) {
        return StrSubstitutor.replace(placeholder,map);
    }


}
