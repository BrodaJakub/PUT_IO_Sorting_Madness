package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

/**
 * Implementacja strategii sortowania wykorzystująca algorytm Heap Sort.
 * <p>
 * Algorytm Heap Sort polega na:
 * <ol>
 * <li>zbudowaniu kopca maksymalnego (max-heap) z elementów listy,</li>
 * <li>wielokrotnym przenoszeniu największego elementu (korzenia kopca)
 * na koniec listy,</li>
 * <li>przywracaniu własności kopca dla pozostałych elementów.</li>
 * </ol>
 * </p>
 *
 *
 */
public class HeapSortStrategy extends AbstractSortingStrategy {

    /**
     * Zwraca nazwę algorytmu sortowania.
     *
     * @return nazwa algorytmu
     */
    @Override
    public String getName() {
        return "Heap Sort";
    }

    /**
     * Sortuje listę elementów przy użyciu algorytmu Heap Sort.
     * <p>
     * Metoda najpierw buduje kopiec maksymalny z przekazanej listy,
     * a następnie wielokrotnie przenosi największy element na koniec
     * nieposortowanej części listy.
     * </p>
     *
     * @param array lista elementów do posortowania; sortowanie odbywa się in-place
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        int n = array.size();

        // Budowanie kopca maksymalnego
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        // Wyciąganie elementów z kopca jeden po drugim
        for (int i = n - 1; i > 0; i--) {
            T tmp = array.get(0);
            array.set(0, array.get(i));
            array.set(i, tmp);

            // Przywrócenie własności kopca dla zmniejszonego kopca
            heapify(array, i, 0);
        }
    }

    /**
     * Przywraca własność kopca maksymalnego dla poddrzewa
     * o korzeniu w indeksie {@code i}.
     * <p>
     * Metoda porównuje element w korzeniu z jego dziećmi
     * i w razie potrzeby dokonuje zamiany, a następnie
     * rekurencyjnie naprawia naruszoną strukturę kopca.
     * </p>
     *
     * @param arr lista reprezentująca kopiec
     * @param n   rozmiar kopca (liczba elementów branych pod uwagę)
     * @param i   indeks korzenia poddrzewa
     * @param <T> typ elementów, który musi implementować {@link Comparable}
     */
    private <T extends Comparable<T>> void heapify(List<T> arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr.get(l).compareTo(arr.get(largest)) > 0)
            largest = l;
        if (r < n && arr.get(r).compareTo(arr.get(largest)) > 0)
            largest = r;

        if (largest != i) {
            T swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);

            // Rekurencyjne przywracanie własności kopca
            heapify(arr, n, largest);
        }
    }
}
