package pl.put.poznan.sortingmadness.Logic.strategy;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

import java.util.List;

public interface SortingStrategy {

    <T extends Comparable<T>> SortResult execute(List<T> array);

    String getName();
}
