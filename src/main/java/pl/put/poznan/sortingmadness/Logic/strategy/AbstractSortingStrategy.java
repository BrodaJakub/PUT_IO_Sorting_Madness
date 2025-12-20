package pl.put.poznan.sortingmadness.Logic.strategy;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

public abstract class AbstractSortingStrategy implements SortingStrategy {

	public <T extends Comparable<T>> SortResult execute(T[] input) {

		long start = System.nanoTime();
		sort(input);
		long end = System.nanoTime();

		return new SortResult(getName(), end - start);
	}

	protected abstract <T extends Comparable<T>> void sort(T[] array);
}
