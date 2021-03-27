package com.andreymironov.inmemorycache.lfu;

import com.andreymironov.inmemorycache.Cache;

import java.util.*;

public class LFUCache extends Cache {
    private final HashMap<String, LfuCacheValueContainer> storage;

    public LFUCache(int maxSize) {
        super(maxSize);
        storage = new HashMap<>(maxSize);
    }

    @Override
    public Object get(String key) {
        if (storage.containsKey(key)) {
            var cacheValue = storage.get(key);
            incrementFrequency(cacheValue);
            return cacheValue.getValue();
        } else {
            throw new NoSuchElementException("No value found by key " + key);
        }
    }

    private void incrementFrequency(LfuCacheValueContainer cacheValue) {
        var frequency = cacheValue.getFrequency();
        if (frequency < Long.MAX_VALUE) {
            cacheValue.setFrequency(frequency + 1);
        } else {
            storage.values().forEach(this::decrementFrequencyIfPossible);
        }
    }

    private void decrementFrequencyIfPossible(LfuCacheValueContainer cacheValue) {
        var frequency = cacheValue.getFrequency();
        if (frequency > Long.MIN_VALUE) {
            cacheValue.setFrequency(frequency - 1);
        }
    }

    @Override
    public void put(String key, Object value) {
        removeKeyIfNecessary();
        storage.put(
                key,
                LfuCacheValueContainer.builder().frequency(0).value(value).build());
    }

    private void removeKeyIfNecessary() {
        if (Objects.equals(maxSize, storage.size())) {
            var keyToRemove = storage.entrySet().stream()
                    .min(Comparator.comparing(entry -> entry.getValue().getFrequency()))
                    .map(Map.Entry::getKey)
                    .orElseThrow(() -> new NoSuchElementException("No key found to remove"));
            storage.remove(keyToRemove);
        }
    }

    @Override
    public Set<String> getKeySet() {
        return storage.keySet();
    }
}
