package com.andreymironov.inmemorycache.lfu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LfuCacheValueContainer {
    private Object value;
    private long frequency;
}
