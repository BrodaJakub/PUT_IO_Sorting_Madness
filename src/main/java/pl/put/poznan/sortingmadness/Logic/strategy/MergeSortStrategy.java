package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

/**
 * Implementacja strategii sortowania wykorzystująca algorytm Merge Sort.
 * <p>
 * Algorytm Merge Sort jest algorytmem typu „dziel i zwyciężaj”.
 * Polega na rekurencyjnym dzieleniu listy na coraz mniejsze podlisty,
 * aż do uzyskania fragmentów jednoelementowych, a następnie
 * scalaniu ich w posortowaną całość.
 * </p>
 *
 * <p>
 * Podczas etapu scalania tworzona jest tymczasowa tablica pomocnicza,
 * do której przepisywane są elementy w odpowiedniej kolejności,
 * a następnie kopiowane z powrotem do listy wejściowej.
 * </p>
 *
 *
 */
public class MergeSortStrategy extends AbstractSortingStrategy {

    /**
     * Zwraca nazwę algorytmu sortowania.
     *
     * @return nazwa algorytmu
     */
    @Override
    public String getName() {
        return "Merge Sort";
    }

    /**
     * Sortuje listę elementów przy użyciu algorytmu Merge Sort.
     * <p>
     * Metoda inicjuje rekurencyjny proces dzielenia i scalania listy.
     * </p>
     *
     * @param array lista elementów do posortowania
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        mergeSort(array, 0, array.size() - 1);
    }

    /**
     * Rekurencyjna metoda realizująca etap dzielenia algorytmu Merge Sort.
     *
     * @param arr   lista elementów do posortowania
     * @param left  indeks początkowy rozpatrywanego fragmentu listy
     * @param right indeks końcowy rozpatrywanego fragmentu listy
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    private <T extends Comparable<T>> void mergeSort(List<T> arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    /**
     * Scala dwa posortowane fragmenty listy w jeden spójny,
     * posortowany fragment.
     * <p>
     * Metoda wykorzystuje pomocniczą tablicę tymczasową,
     * do której kopiowane są elementy w odpowiedniej kolejności,
     * a następnie przepisywane z powrotem do listy wejściowej.
     * </p>
     *
     * @param arr   lista zawierająca scalane fragmenty
     * @param left  indeks początkowy pierwszego fragmentu
     * @param mid   indeks końcowy pierwszego fragmentu
     * @param right indeks końcowy drugiego fragmentu
     * @param <T>   typ elementów, który musi implementować {@link Comparable}
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(List<T> arr, int left, int mid, int right) {
        T[] temp = (T[]) new Comparable[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            temp[k++] = (arr.get(i).compareTo(arr.get(j)) <= 0)
                    ? arr.get(i++)
                    : arr.get(j++);
        }

        while (i <= mid)
            temp[k++] = arr.get(i++);
        while (j <= right)
            temp[k++] = arr.get(j++);

        for (int m = 0; m < temp.length; m++) {
            arr.set(left + m, temp[m]);
        }
    }

}
