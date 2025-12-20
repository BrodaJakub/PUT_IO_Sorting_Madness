package pl.put.poznan.sortingmadness.Logic.strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

public abstract class AbstractSortingStrategy implements SortingStrategy {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public SortResult execute(int[] input) {
    	logger.info("Początek  sortowania {}. elementowej tabeli algorytmem {}. ", input.length ,getName());
        int[] copy = input.clone();

        long start = System.nanoTime();
        sort(copy);
        long end = System.nanoTime();
    	logger.info("Koniec sortowania {}. elementowej tabeli  algorytmem {}. ",input.length,getName());
        return new SortResult(getName(), end - start, copy);

    }

    protected abstract void sort(int[] array);
}
