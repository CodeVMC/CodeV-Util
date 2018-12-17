package net.codev.util.lore.paragrah;

import net.codev.util.lore.Lore;
import net.codev.util.lore.paragrah.line.ParagraphLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParagraphListLore implements Lore {

    private ArrayList<ParagraphLine> lines = new ArrayList<>();

    @Override
    public List<String> get(Map<String, ?> map) {
        return lines
                .stream()
                .collect(ArrayList::new,(list,line)->list.add(line.getLine(map)),ArrayList::addAll);
    }

    @Override
    public void addLine(ParagraphLine line) {
        lines.add(line);
    }

}
