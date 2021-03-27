package com.andreymironov.inmemorycache;

import com.andreymironov.inmemorycache.lfu.LFUCache;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class LFUCacheTest {
    @Test
    public void testEviction() {
        var cache = new LFUCache(2);
        cache.put("1", 1);
        cache.put("2", 2);

        cache.get("2");
        cache.get("2");
        cache.get("1");

        cache.put("3", 3);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(cache.getKeySet()).containsExactlyInAnyOrder("2", "3");
            softly.assertThat(cache.get("2")).isEqualTo(2);
            softly.assertThat(cache.get("3")).isEqualTo(3);
            softly.assertThatThrownBy(() -> cache.get("1")).isInstanceOf(NoSuchElementException.class);
        });
    }
}