package net.codevmc.util.lore.paragraph.line;

import net.codevmc.util.lore.paragraph.paragraph.Paragraph;

import java.io.Serializable;
import java.util.Map;

public interface ParagraphLine extends Serializable {
    String getLine(Map<String,? extends Object> map);
    void addParagraph(Paragraph paragraph);
}
