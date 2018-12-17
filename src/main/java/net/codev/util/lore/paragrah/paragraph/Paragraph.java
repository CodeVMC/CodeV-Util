package net.codev.util.lore.paragrah.paragraph;

import java.io.Serializable;
import java.util.Map;

public interface Paragraph extends Serializable {

    String getParagraph(Map<String,? extends Object> map);

}
