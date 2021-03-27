package com.andreymironov.inmemorycache;

import com.andreymironov.inmemorycache.lru.LRUCache;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class LRUCacheTest {
    @Test
    public void testEviction() {
        var cache = new LRUCache(2);
        cache.put("1", 1);
        cache.put("2", 2);

        cache.get("2");
        cache.get("2");
        cache.get("1");

        cache.put("3", 3);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(cache.getKeySet()).containsExactlyInAnyOrder("1", "3");
            softly.assertThat(cache.get("1")).isEqualTo(1);
            softly.assertThat(cache.get("3")).isEqualTo(3);
            softly.assertThatThrownBy(() -> cache.get("2")).isInstanceOf(NoSuchElementException.class);
        });
    }
}