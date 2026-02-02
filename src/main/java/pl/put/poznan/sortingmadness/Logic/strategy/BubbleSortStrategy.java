package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

/**
 * Implementacja strategii sortowania wykorzystująca algorytm Bubble Sort.
 * <p>
 * Algorytm polega na wielokrotnym porównywaniu sąsiednich elementów
 * i zamienianiu ich miejscami, jeśli są w złej kolejności.
 * </p>
 *
 * Zastosowana optymalizacja polega na przerwaniu sortowania,
 * jeśli w danym przebiegu nie dokonano żadnej zamiany.
 *
 *
 */
public class BubbleSortStrategy extends AbstractSortingStrategy {

    /**
     * Zwraca nazwę algorytmu sortowania.
     *
     * @return nazwa algorytmu
     */
    @Override
    public String getName() {
        return "Bubble Sort";
    }

    /**
     * Sortuje listę elementów przy użyciu algorytmu Bubble Sort.
     *
     * @param array lista elementów do posortowania
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        for (int i = 0; i < array.size() - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.size() - 1 - i; j++) {
                if (array.get(j).compareTo(array.get(j + 1)) > 0) {
                    T tmp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, tmp);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }
}
