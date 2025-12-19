package pl.put.poznan.sortingmadness.Logic.context;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.SortingStrategy;

public class SortingContext {

    private SortingStrategy strategy;

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public SortResult execute(int[] data) {
        if (strategy == null) {
            throw new IllegalStateException("Nie ustawiono strategii sortowania");
        }
        return strategy.execute(data);
    }
}
