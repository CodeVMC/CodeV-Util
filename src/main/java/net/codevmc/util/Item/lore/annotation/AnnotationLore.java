package net.codevmc.util.Item.lore.annotation;

import net.codevmc.util.Item.lore.Lore;


public abstract class AnnotationLore<T> extends Lore {
    protected final T element;
    public AnnotationLore(T element){
        this.element = element;
    }
}
