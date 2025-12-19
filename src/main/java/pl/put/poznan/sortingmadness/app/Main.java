package pl.put.poznan.sortingmadness.app;

import pl.put.poznan.sortingmadness.Logic.context.SortingContext;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[] data = {7, 3, 9, 1, 5, 4, 8, 2, 6};

        List<SortingStrategy> strategies = List.of(
                new BubbleSortStrategy(),
                new SelectionSortStrategy(),
                new InsertionSortStrategy()
        );

        SortingContext context = new SortingContext();

        for (SortingStrategy strategy : strategies) {
            context.setStrategy(strategy);
            SortResult result = context.execute(data);

            System.out.println("Algorytm: " + result.getAlgorithmName());
            System.out.println("Czas wykonania (ns): " + result.getExecutionTimeNano());
            System.out.print("Wynik: ");
            for (int v : result.getSortedArray()) {
                System.out.print(v + " ");
            }
            System.out.println("\n----------------------------");
        }
    }
}
