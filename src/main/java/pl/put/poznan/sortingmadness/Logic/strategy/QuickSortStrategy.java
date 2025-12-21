package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

public class QuickSortStrategy extends AbstractSortingStrategy {
    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        quickSort(array, 0, array.size() - 1);
    }

    private <T extends Comparable<T>> void quickSort(List<T> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

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
