package pl.put.poznan.sortingmadness.Logic.strategy;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

public interface SortingStrategy {

    SortResult execute(int[] array);

    String getName();
}
