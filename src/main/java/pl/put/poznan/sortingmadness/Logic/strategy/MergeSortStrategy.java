package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

public class MergeSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        mergeSort(array, 0, array.size() - 1);
    }

    private <T extends Comparable<T>> void mergeSort(List<T> arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private <T extends Comparable<T>> void merge(List<T> arr, int left, int mid, int right) {
        T[] temp = (T[]) new Comparable[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            temp[k++] = (arr.get(i).compareTo(arr.get(j)) <= 0) ? arr.get(i++) : arr.get(j++);
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
