package pl.put.poznan.sortingmadness.Logic.strategy;

public class SelectionSortStrategy extends AbstractSortingStrategy {

	@Override
	public String getName() {
		return "Selection Sort";
	}

	@Override
	protected <T extends Comparable<T>> void sort(T[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int minIdx = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[j].compareTo(array[minIdx]) < 0) {
					minIdx = j;
				}
			}
			T tmp = array[i];
			array[i] = array[minIdx];
			array[minIdx] = tmp;
		}
	}
}
