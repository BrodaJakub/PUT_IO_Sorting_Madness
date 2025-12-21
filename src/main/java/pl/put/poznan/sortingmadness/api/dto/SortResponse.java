package pl.put.poznan.sortingmadness.api.dto;

import java.util.List;

public class SortResponse<T extends Comparable<T>> {

    private final String algorithm;
    private final double executionTimeMs;
    private final List<T> sortedData;

    public SortResponse(String algorithm, double executionTimeMs, List<T> sortedData) {
        this.algorithm = algorithm;
        this.executionTimeMs = executionTimeMs;
        this.sortedData = sortedData;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    public List<T> getSortedData() {
        return sortedData;
    }
}