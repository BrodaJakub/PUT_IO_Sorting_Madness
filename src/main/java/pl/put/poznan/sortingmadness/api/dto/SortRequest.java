package pl.put.poznan.sortingmadness.api.dto;

import java.util.List;

public class SortRequest<T extends Comparable<T>> {

    private final String algorithm;
    private final List<T> data;

    public SortRequest(String algorithm, List<T> data) {
        this.algorithm = algorithm;
        this.data = data;
    }

    public String getAlgorithm() {
        return algorithm;
    }


    public List<T> getData() {
        return data;
    }
}