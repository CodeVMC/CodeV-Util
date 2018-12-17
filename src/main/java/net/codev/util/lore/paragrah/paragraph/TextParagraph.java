package net.codev.util.lore.paragrah.paragraph;

import java.util.Map;

public class TextParagraph implements Paragraph {

    private String text;

    public TextParagraph(String s){
        this.text = s;
    }

    @Override
    public String getParagraph(Map<String,? extends Object> map) {
        return text;
    }
}
