package pl.put.poznan.sortingmadness.Logic.strategy;

public class BubbleSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    protected void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
