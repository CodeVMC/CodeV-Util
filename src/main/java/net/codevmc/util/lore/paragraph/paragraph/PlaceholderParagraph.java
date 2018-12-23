package net.codevmc.util.lore.paragraph.paragraph;

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
