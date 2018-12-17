package net.codev.util.lore;

import net.codev.util.lore.paragrah.line.ParagraphLine;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Lore extends Serializable {

    List<String> get(Map<String,? extends Object> map);
    void addLine(ParagraphLine line);

}
