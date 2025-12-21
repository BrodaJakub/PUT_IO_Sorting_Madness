package pl.put.poznan.sortingmadness.Logic.service;

import org.springframework.stereotype.Service;

import java.util.Map;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.*;
import pl.put.poznan.sortingmadness.Logic.context.SortingContext;

import java.util.List;

@Service
public class SortingService {

    private final Map<String, SortingStrategy> strategies = Map.of(
            "bubble", new BubbleSortStrategy(),
            "selection", new SelectionSortStrategy(),
            "insertion", new InsertionSortStrategy(),
            "merge", new MergeSortStrategy(),
            "quick", new QuickSortStrategy(),
            "heap", new HeapSortStrategy()
    );

    public <T extends Comparable<T>> SortResult sort(String algorithm, List<T> data) {

        SortingStrategy strategy = strategies.get(algorithm.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Nieznany algorytm: " + algorithm);
        }

        SortingContext context = new SortingContext();
        context.setStrategy(strategy);

        return context.execute(data);
    }
}
