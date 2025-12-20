package pl.put.poznan.sortingmadness.Logic.strategy;

public class SortResult<T> {

	private final String algorithmName;
	private final long executionTimeNano;
	private final T[] sortedArray;

	public SortResult(String algorithmName, long executionTimeNano, T[] sortedArray) {
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

	public T[] getSortedArray() {
		return sortedArray;
	}
}
