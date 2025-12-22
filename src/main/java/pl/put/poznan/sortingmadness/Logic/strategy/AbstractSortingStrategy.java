package pl.put.poznan.sortingmadness.Logic.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.put.poznan.sortingmadness.Logic.model.SortResult;

import java.util.List;
import java.util.List;

/**
 * Abstrakcyjna klasa bazowa dla strategii sortowania.
 * <p>
 * Zapewnia wspólną logikę wykonywania algorytmów sortowania:
 * <ul>
 *     <li>logowanie początku i końca sortowania,</li>
 *     <li>pomiar czasu wykonania algorytmu,</li>
 *     <li>zwracanie wyniku sortowania w obiekcie {@link SortResult}.</li>
 * </ul>
 *
 * Klasy dziedziczące powinny implementować metodę {@link #sort(List)},
 * zawierającą właściwą logikę sortowania.
 *
 *
 */
public abstract class AbstractSortingStrategy implements SortingStrategy {

    /** Logger wykorzystywany do zapisywania informacji o przebiegu sortowania */
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Wykonuje sortowanie przekazanej listy oraz mierzy czas jego trwania.
     *
     * @param input lista elementów do posortowania
     * @param <T> typ elementów, który musi implementować {@link Comparable}
     * @return obiekt {@link SortResult} zawierający nazwę algorytmu
     *         oraz czas jego wykonania w nanosekundach
     */
    public <T extends Comparable<T>> SortResult execute(List<T> input) {
        logger.info("Początek  sortowania {}. elementowej tabeli algorytmem {}. ", input.size(), getName());

        long start = System.nanoTime();
        sort(input);
        long end = System.nanoTime();
        logger.info("Koniec sortowania {}. elementowej tabeli  algorytmem {}. ", input.size(), getName());

        return new SortResult(getName(), end - start);
    }

    /**
     * Metoda odpowiedzialna za właściwą logikę sortowania.
     * <p>
     * Musi zostać zaimplementowana przez klasy dziedziczące.
     * </p>
     *
     * @param array lista elementów do posortowania
     * @param <T> typ elementów, który musi implementować {@link Comparable}
     */
    protected abstract <T extends Comparable<T>> void sort(List<T> array);
}
