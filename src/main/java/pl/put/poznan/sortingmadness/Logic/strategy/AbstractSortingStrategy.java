package pl.put.poznan.sortingmadness.Logic.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

public abstract class AbstractSortingStrategy implements SortingStrategy {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public <T extends Comparable<T>> SortResult execute(T[] input) {
		logger.info("Początek  sortowania {}. elementowej tabeli algorytmem {}. ", input.length, getName());

		long start = System.nanoTime();
		sort(input);
		long end = System.nanoTime();
		logger.info("Koniec sortowania {}. elementowej tabeli  algorytmem {}. ", input.length, getName());

		return new SortResult(getName(), end - start);
	}

	protected abstract <T extends Comparable<T>> void sort(T[] array);
}
