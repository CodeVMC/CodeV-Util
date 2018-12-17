package net.codev.util.lore.paragrah.line;

import net.codev.util.lore.paragrah.paragraph.Paragraph;

import java.io.Serializable;
import java.util.Map;

public interface ParagraphLine extends Serializable {
    String getLine(Map<String,? extends Object> map);
    void addParagraph(Paragraph paragraph);
}
