package pl.put.poznan.sortingmadness.Logic.strategy;

import java.util.List;

public class SelectionSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Selection Sort";
    }

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
