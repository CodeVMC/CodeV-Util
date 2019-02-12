package net.codevmc.util.Item.lore.annotation;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AnnotationManager {

    private static final LoadingCache<Object,Class> cache = CacheBuilder
            .newBuilder()
            .maximumSize(100)
            .initialCapacity(10)
            .concurrencyLevel(8)
            .expireAfterAccess(20, TimeUnit.MINUTES)
            .build(new CacheLoader<Object, Class>() {
                @Override
                public Class load(Object o) {
                    return getClassFromAnnotation(o);
                }
            });

    public static List<String> getLore(Object o){
        try {
            return ((AnnotationLore)cache.get(o).getConstructor(Object.class).newInstance(o)).get();
        } catch (ExecutionException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static Class getClassFromAnnotation(Object o){
        Lore annotation = o.getClass().getAnnotation(Lore.class);
        if(annotation==null)
            return DefaultAnnotationLore.class;
        return annotation.value();
    }

    private static class DefaultAnnotationLore extends AnnotationLore<Object>{
        public DefaultAnnotationLore(Object o) {
            super(o);
        }
        @Override
        public List<String> get() {
            return Lists.newArrayList(t.toString());
        }
    }

}
