package pl.put.poznan.sortingmadness.Logic.strategy;

public class SortResult {

    private final String algorithmName;
    private final long executionTimeNano;
    private final int[] sortedArray;

    public SortResult(String algorithmName, long executionTimeNano, int[] sortedArray) {
        this.algorithmName = algorithmName;
        this.executionTimeNano = executionTimeNano;
        this.sortedArray = sortedArray;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public long getExecutionTimeNano() {
        return executionTimeNano;
    }

    public int[] getSortedArray() {
        return sortedArray;
    }
}
