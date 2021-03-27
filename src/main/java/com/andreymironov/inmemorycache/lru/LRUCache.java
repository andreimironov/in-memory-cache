package com.andreymironov.inmemorycache.lru;

import com.andreymironov.inmemorycache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class LRUCache extends Cache {
    private final LinkedHashMap<String, Object> storage;

    public LRUCache(int maxSize) {
        super(maxSize);
        storage = new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
                return size() > maxSize;
            }
        };
    }

    @Override
    public Object get(String key) {
        if (storage.containsKey(key)) {
            return storage.get(key);
        } else {
            throw new NoSuchElementException("No value found by key " + key);
        }
    }

    @Override
    public void put(String key, Object value) {
        storage.put(key, value);
    }

    @Override
    public Set<String> getKeySet() {
        return storage.keySet();
    }
}
