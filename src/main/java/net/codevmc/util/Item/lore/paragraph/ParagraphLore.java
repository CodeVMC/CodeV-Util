package net.codevmc.util.Item.lore.paragraph;

import net.codevmc.util.Item.lore.Lore;
import net.codevmc.util.serialization.Serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParagraphLore extends Lore {

    @Serialization
    private List<Paragraph> paragraphList = new ArrayList<>();

    private Lock lock = new ReentrantLock();

    @Override
    public List<String> get() {
        try {
            lock.lock();
            return paragraphList
                    .stream()
                    .collect(ArrayList::new, (list, p) -> list.addAll(p.getParagraph()), ArrayList::addAll);
        } finally {
            lock.unlock();
        }
    }

    public void addParagraph(Paragraph paragraph) {
        lock.lock();
        this.paragraphList.add(paragraph);
        lock.unlock();
    }

    public void removeParagraph(Paragraph paragraph) {
        lock.lock();
        this.paragraphList.remove(paragraph);
        lock.unlock();
    }

    public boolean hasParagraph(Paragraph paragraph) {
        try {
            lock.lock();
            return this.paragraphList.contains(paragraph);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void asyncUpdate() {
        lock.lock();
        paragraphList.forEach(Paragraph::asyncUpdate);
        lock.unlock();
    }

    @Override
    public void update() {
        lock.lock();
        paragraphList.forEach(Paragraph::update);
        lock.unlock();
    }
}
