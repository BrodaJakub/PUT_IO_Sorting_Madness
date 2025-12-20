package pl.put.poznan.sortingmadness.Logic.strategy;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

public abstract class AbstractSortingStrategy implements SortingStrategy {

    @Override
    public SortResult execute(int[] input) {
        int[] copy = input.clone();

        long start = System.nanoTime();
        sort(copy);
        long end = System.nanoTime();

        return new SortResult(getName(), end - start, copy);
    }

    protected abstract void sort(int[] array);
}
