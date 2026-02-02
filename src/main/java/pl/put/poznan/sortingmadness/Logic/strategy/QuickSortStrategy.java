package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

/**
 * Implementacja strategii sortowania wykorzystująca algorytm Quick Sort.
 * <p>
 * Algorytm Quick Sort jest algorytmem typu „dziel i zwyciężaj”.
 * Polega na wyborze elementu rozdzielającego (pivotu), a następnie
 * podziale listy na dwie części:
 * <ul>
 * <li>elementy mniejsze od pivotu,</li>
 * <li>elementy większe lub równe pivotowi.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Proces sortowania wykonywany jest rekurencyjnie dla obu podlist,
 * aż do osiągnięcia fragmentów jednoelementowych lub pustych.
 * </p>
 *
 * <p>
 * W tej implementacji jako pivot wybierany jest ostatni element listy.
 * </p>
 *
 * 
 * 
 */
public class QuickSortStrategy extends AbstractSortingStrategy {

    /**
     * Zwraca nazwę algorytmu sortowania.
     *
     * @return nazwa algorytmu
     */
    @Override
    public String getName() {
        return "Quick Sort";
    }

    /**
     * Sortuje listę elementów przy użyciu algorytmu Quick Sort.
     * <p>
     * Metoda inicjuje rekurencyjny proces sortowania
     * dla całego zakresu listy.
     * </p>
     *
     * @param array lista elementów do posortowania; sortowanie odbywa się in-place
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        quickSort(array, 0, array.size() - 1);
    }

    /**
     * Rekurencyjna metoda realizująca algorytm Quick Sort
     * dla określonego fragmentu listy.
     *
     * @param arr  lista elementów do posortowania
     * @param low  indeks początkowy sortowanego fragmentu
     * @param high indeks końcowy sortowanego fragmentu
     * @param <T>  typ elementów, który musi implementować {@link Comparable}
     */
    private <T extends Comparable<T>> void quickSort(List<T> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    /**
     * Dzieli listę na dwa fragmenty względem wybranego pivotu.
     * <p>
     * Elementy mniejsze od pivotu umieszczane są po jego lewej stronie,
     * natomiast elementy większe lub równe pivotowi — po prawej.
     * </p>
     *
     * @param arr  lista elementów podlegająca podziałowi
     * @param low  indeks początkowy rozpatrywanego fragmentu
     * @param high indeks końcowy rozpatrywanego fragmentu; element
     *             pod tym indeksem pełni rolę pivotu
     * @param <T>  typ elementów, który musi implementować {@link Comparable}
     * @return indeks, na którym po zakończeniu partycjonowania
     *         znajduje się pivot
     */
    private <T extends Comparable<T>> int partition(List<T> arr, int low, int high) {
        T pivot = arr.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr.get(j).compareTo(pivot) < 0) {
                i++;
                T tmp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, tmp);
            }
        }

        T tmp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, tmp);

        return i + 1;
    }
}
