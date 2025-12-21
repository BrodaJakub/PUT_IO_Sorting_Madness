package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

public class InsertionSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    protected <T extends Comparable<T>> void sort(List<T> array) {
        for (int i = 1; i < array.size();
             i++) {
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
