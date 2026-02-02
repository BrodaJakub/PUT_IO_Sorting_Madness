package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

/**
 * Implementacja strategii sortowania wykorzystująca algorytm Insertion Sort.
 * <p>
 * Algorytm Insertion Sort polega na stopniowym budowaniu
 * posortowanej części listy poprzez wstawianie kolejnych elementów
 * w odpowiednie miejsce.
 * </p>
 *
 * <p>
 * Dla każdego elementu algorytm porównuje go z elementami
 * znajdującymi się po jego lewej stronie i przesuwa większe
 * wartości o jedną pozycję w prawo, aż do znalezienia właściwego
 * miejsca dla aktualnie wstawianego elementu.
 * </p>
 *
 * 
 */
public class InsertionSortStrategy extends AbstractSortingStrategy {

    /**
     * Zwraca nazwę algorytmu sortowania.
     *
     * @return nazwa algorytmu
     */
    @Override
    public String getName() {
        return "Insertion Sort";
    }

    /**
     * Sortuje listę elementów przy użyciu algorytmu Insertion Sort.
     * <p>
     * Metoda iteruje po elementach listy, traktując część po lewej stronie
     * aktualnego indeksu jako posortowaną, i wstawia bieżący element
     * w odpowiednie miejsce tej części.
     * </p>
     *
     * @param array lista elementów do posortowania; sortowanie odbywa się in-place
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        for (int i = 1; i < array.size(); i++) {
            T key = array.get(i);
            int j = i - 1;

            while (j >= 0 && array.get(j).compareTo(key) > 0) {
                array.set(j + 1, array.get(j));
                j--;
            }
            array.set(j + 1, key);
        }
    }
}
