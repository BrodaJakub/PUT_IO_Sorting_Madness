package pl.put.poznan.sortingmadness.Logic.strategy;

public class SelectionSortStrategy extends AbstractSortingStrategy {

    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    protected void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            int tmp = array[i];
            array[i] = array[minIdx];
            array[minIdx] = tmp;
        }
    }
}
