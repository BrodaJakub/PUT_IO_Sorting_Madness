package pl.put.poznan.sortingmadness.api.dto;

public class SortResponse {

    private final String algorithm;
    private final double executionTimeMs;
    private final int[] sortedData;

    public SortResponse(String algorithm, double executionTimeMs, int[] sortedData) {
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

    public int[] getSortedData() {
        return sortedData;
    }
}