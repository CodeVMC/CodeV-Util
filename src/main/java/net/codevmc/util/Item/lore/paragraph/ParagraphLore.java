package net.codevmc.util.Item.lore.paragraph;

import net.codevmc.util.Item.lore.Lore;
import net.codevmc.util.Item.lore.util.LockArrayLore;
import net.codevmc.util.serialization.Serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParagraphLore extends Lore {

    @Serialization
    private LockArrayLore lockLore = new LockArrayLore();

    @Override
    public List<String> get() {
        return lockLore.get();
    }

    public void addParagraph(Paragraph paragraph) {
        lockLore.add(paragraph);
    }

    public void removeParagraph(Paragraph paragraph) {
        lockLore.remove(paragraph);
    }

    public boolean hasParagraph(Paragraph paragraph) {
        return lockLore.has(paragraph);
    }

    @Override
    public void asyncUpdate() {
        lockLore.asyncUpdate();
    }

    @Override
    public void update() {
        lockLore.update();
    }
}
