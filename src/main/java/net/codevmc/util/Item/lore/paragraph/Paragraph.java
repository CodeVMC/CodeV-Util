package net.codevmc.util.Item.lore.paragraph;

import java.util.List;

public interface Paragraph {

    List<String> getParagraph();
    default void update(){}
    default void asyncUpdate(){}

}
