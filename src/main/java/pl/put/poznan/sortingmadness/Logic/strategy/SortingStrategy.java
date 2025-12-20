package pl.put.poznan.sortingmadness.Logic.strategy;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

public interface SortingStrategy {

	<T extends Comparable<T>> SortResult execute(T[] array);

	String getName();
}
