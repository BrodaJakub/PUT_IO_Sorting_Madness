package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

public class HeapSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Heap Sort";
    }

    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        int n = array.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            T tmp = array.get(0);
            array.set(0, array.get(i));
            array.set(i, tmp);
            heapify(array, i, 0);
        }
    }

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
            heapify(arr, n, largest);
        }
    }
}
