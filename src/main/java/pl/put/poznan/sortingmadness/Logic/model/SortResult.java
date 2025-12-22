package pl.put.poznan.sortingmadness.Logic.model;

public class SortResult {

    private final String algorithmName;
    private final long executionTimeNano;

    public SortResult(String algorithmName, long executionTimeNano) {
        this.algorithmName = algorithmName;
        this.executionTimeNano = executionTimeNano;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public long getExecutionTimeNano() {
        return executionTimeNano;
    }
}
