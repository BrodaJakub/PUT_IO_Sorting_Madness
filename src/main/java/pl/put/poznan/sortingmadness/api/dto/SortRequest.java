package pl.put.poznan.sortingmadness.api.dto;

import java.util.List;

public class SortRequest<T extends Comparable<T>> {

    private final List<String> algorithms;
    private final List<T> data;

    public SortRequest(List<String> algorithms, List<T> data) {
        this.algorithms = algorithms;
        this.data = data;
    }

    public List<String> getAlgorithms() {
        return algorithms;
    }

    public boolean isValid() {
        return algorithms != null && !algorithms.isEmpty()
                && data != null && !data.isEmpty();
    }


    public List<T> getData() {
        return data;
    }
}