package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

public class BubbleSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        for (int i = 0; i < array.size() - 1;
             i++) {
            boolean swapped = false;
            for (int j = 0; j < array.size() - 1 - i;
                 j++) {
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
