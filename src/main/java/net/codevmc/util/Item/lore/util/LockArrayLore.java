package net.codevmc.util.Item.lore.util;

import net.codevmc.util.Item.lore.Lore;
import net.codevmc.util.serialization.Serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class  LockArrayLore<T extends Lore>  extends Lore {

    @Serialization
    private ArrayList<T> loreArrayList = new ArrayList<>();

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

    public void add(T lore) {
        lock.lock();
        this.loreArrayList.add(lore);
        lock.unlock();
    }

    public void remove(T lore) {
        lock.lock();
        this.loreArrayList.remove(lore);
        lock.unlock();
    }

    public boolean has(T lore) {
        try {
            lock.lock();
            return this.loreArrayList.contains(lore);
        } finally {
            lock.unlock();
        }
    }


}
