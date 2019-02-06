package net.codevmc.util.Item.lore.paragraph.line;

import net.codevmc.util.Item.lore.paragraph.Paragraph;

import java.io.Serializable;
import java.util.Map;

public interface ParagraphLine extends Serializable {
    String getLine(Map<String,? extends Object> map);
    void addParagraph(Paragraph paragraph);
}
