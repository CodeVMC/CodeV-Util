package net.codev.util.lore.paragrah.line;

import net.codev.util.lore.paragrah.paragraph.Paragraph;

import java.util.ArrayList;
import java.util.Map;

public class ListParagraphLine implements ParagraphLine {

    private ArrayList<Paragraph> paragraphArrayList = new ArrayList<>();

    @Override
    public String getLine(Map<String,? extends Object> map) {
        return paragraphArrayList
                .stream()
                .collect(StringBuilder::new,((stringBuilder, paragraph) -> stringBuilder.append(paragraph.getParagraph(map))),
                        StringBuilder::append).toString();
    }

    @Override
    public void addParagraph(Paragraph paragraph) {
        paragraphArrayList.add(paragraph);
    }


}
