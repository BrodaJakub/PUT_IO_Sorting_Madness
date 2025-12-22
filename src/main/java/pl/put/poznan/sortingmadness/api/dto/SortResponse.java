package pl.put.poznan.sortingmadness.api.dto;

import java.util.ArrayList;
import java.util.List;

public class SortResponse<T extends Comparable<T>> {

    public final List<AlgorithmSortResult<T>> algorithmResults;

    public SortResponse() {
        this.algorithmResults = new ArrayList();
    }

    public SortResponse(List<AlgorithmSortResult<T>> algorithmResults) {
        this.algorithmResults = algorithmResults;
    }

    public List<AlgorithmSortResult<T>> getAlgorithmResults() {
        return algorithmResults;
    }

    // Inner class to represent the result of a single algorithm
    public static class AlgorithmSortResult<T extends Comparable<T>> {
        private final String algorithm;
        private final double executionTimeNs;
        private final List<T> sortedData;

        public AlgorithmSortResult(String algorithm, double executionTimeNs, List<T> sortedData) {
            this.algorithm = algorithm;
            this.executionTimeNs = executionTimeNs;
            this.sortedData = sortedData;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public double getExecutionTimeNs() {
            return executionTimeNs;
        }

        public List<T> getSortedData() {
            return sortedData;
        }
    }
}