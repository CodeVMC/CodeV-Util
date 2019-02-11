package net.codevmc.util.Item.lore.util;

import net.codevmc.util.Item.lore.Lore;
import net.codevmc.util.serialization.Serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockArrayLore extends Lore {

    @Serialization
    private ArrayList<Lore> loreArrayList = new ArrayList<>();

    private Lock lock;

    public LockArrayLore(){
        this(new ReentrantLock());
    }

    public LockArrayLore(Lock lock){
        this.lock=lock;
    }

    @Override
    public List<String> get() {
        try {
            lock.lock();
            return loreArrayList
                    .stream()
                    .collect(ArrayList::new, (list, p) -> list.addAll(p.get()), ArrayList::addAll);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void asyncUpdate() {
        lock.lock();
        loreArrayList.forEach(Lore::asyncUpdate);
        lock.unlock();
    }

    @Override
    public void update() {
        lock.lock();
        loreArrayList.forEach(Lore::update);
        lock.unlock();
    }

    public void add(Lore paragraph) {
        lock.lock();
        this.loreArrayList.add(paragraph);
        lock.unlock();
    }

    public void remove(Lore paragraph) {
        lock.lock();
        this.loreArrayList.remove(paragraph);
        lock.unlock();
    }

    public boolean has(Lore paragraph) {
        try {
            lock.lock();
            return this.loreArrayList.contains(paragraph);
        } finally {
            lock.unlock();
        }
    }


}
