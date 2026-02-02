package pl.put.poznan.sortingmadness.api.service;

import pl.put.poznan.sortingmadness.api.dto.SortResponse;

public class SortWithCacheResult<T extends Comparable<T>> {

    private final SortResponse<T> response;
    private final boolean cached;

    public SortWithCacheResult(SortResponse<T> response, boolean cached) {
        this.response = response;
        this.cached = cached;
    }

    public SortResponse<T> getResponse() {
        return response;
    }

    public boolean isCached() {
        return cached;
    }
}
