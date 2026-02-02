package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

/**
 * Implementacja strategii sortowania wykorzystująca algorytm Selection Sort.
 * <p>
 * Algorytm Selection Sort polega na wielokrotnym wyszukiwaniu najmniejszego
 * elementu w nieposortowanej części listy i umieszczaniu go na początku
 * tej części poprzez zamianę z aktualnym elementem.
 * </p>
 *
 * <p>
 * W każdej iteracji algorytm zakłada, że element na pozycji {@code i}
 * jest najmniejszy, a następnie porównuje go z pozostałymi elementami,
 * aktualizując indeks minimum w razie potrzeby.
 * </p>
 *
 *
 */
public class SelectionSortStrategy extends AbstractSortingStrategy {

    /**
     * Zwraca nazwę algorytmu sortowania.
     *
     * @return nazwa algorytmu
     */
    @Override
    public String getName() {
        return "Selection Sort";
    }

    /**
     * Sortuje listę elementów przy użyciu algorytmu Selection Sort.
     * <p>
     * Metoda iteruje po liście, wybierając w każdej iteracji najmniejszy
     * element z nieposortowanej części i umieszczając go na właściwej pozycji.
     * </p>
     *
     * @param array lista elementów do posortowania; sortowanie odbywa się in-place
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        for (int i = 0; i < array.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(j).compareTo(array.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            T tmp = array.get(i);
            array.set(i, array.get(minIdx));
            array.set(minIdx, tmp);
        }
    }
}
