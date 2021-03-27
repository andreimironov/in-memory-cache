package com.andreymironov.inmemorycache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public abstract class Cache {
    protected final int maxSize;

    public abstract Object get(String key);

    public abstract void put(String key, Object value);

    public abstract Set<String> getKeySet();
}
