package pl.put.poznan.sortingmadness.Logic.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

import java.util.List;
import java.util.List;

public abstract class AbstractSortingStrategy implements SortingStrategy {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public <T extends Comparable<T>> SortResult execute(List<T> input) {
        logger.info("Początek  sortowania {}. elementowej tabeli algorytmem {}. ", input.size(), getName());

        long start = System.nanoTime();
        sort(input);
        long end = System.nanoTime();
        logger.info("Koniec sortowania {}. elementowej tabeli  algorytmem {}. ", input.size(), getName());

        return new SortResult(getName(), end - start);
    }

    protected abstract <T extends Comparable<T>> void sort(List<T> array);
}
