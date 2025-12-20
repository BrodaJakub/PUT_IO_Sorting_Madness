package pl.put.poznan.sortingmadness.Logic.strategy;

public class InsertionSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    protected void sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}
