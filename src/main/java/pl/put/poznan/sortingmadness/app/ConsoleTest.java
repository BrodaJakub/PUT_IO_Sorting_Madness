package pl.put.poznan.sortingmadness.app;

import pl.put.poznan.sortingmadness.Logic.context.SortingContext;
import pl.put.poznan.sortingmadness.Logic.model.SortResult;
import pl.put.poznan.sortingmadness.Logic.strategy.*;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleTest {
    final static Logger logger = LoggerFactory.getLogger(ConsoleTest.class);

    public static void main(String[] args) {
        logger.debug("Początek programu");

        List<Integer> data = List.of(new Integer[]{7, 3, 9, 1, 5, 4, 8, 2, 6});

        List<SortingStrategy> strategies = List.of(
                new BubbleSortStrategy(),
                new SelectionSortStrategy(),
                new InsertionSortStrategy(),
                new MergeSortStrategy(),
                new QuickSortStrategy(),
                new HeapSortStrategy()
        );

        SortingContext context = new SortingContext();

        for (SortingStrategy strategy : strategies) {
            List<Integer> datac = new ArrayList<Integer>(data);
            context.setStrategy(strategy);
            SortResult result = context.execute(datac);

            System.out.println("Algorytm: " + result.getAlgorithmName());
            System.out.println("Czas wykonania (ns): " + result.getExecutionTimeNano());
            System.out.print("Wynik: ");
            for (int v : datac) {
                System.out.print(v + " ");
            }
            System.out.println("\n----------------------------");
        }
    }
}
