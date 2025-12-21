package pl.put.poznan.sortingmadness.Logic.strategy;

public class BubbleSortStrategy extends AbstractSortingStrategy {

	@Override
	public String getName() {
		return "Bubble Sort";
	}

	@Override
	protected <T extends Comparable<T>> void sort(T[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			boolean swapped = false;
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j].compareTo(array[j + 1]) > 0) {
					T tmp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = tmp;
					swapped = true;
				}
			}
			if (!swapped)
				break;
		}
	}
}
