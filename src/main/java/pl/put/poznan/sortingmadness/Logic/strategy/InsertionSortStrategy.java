package pl.put.poznan.sortingmadness.Logic.strategy;

public class InsertionSortStrategy extends AbstractSortingStrategy {

	@Override
	public String getName() {
		return "Insertion Sort";
	}

	@Override
	protected <T extends Comparable<T>> void sort(T[] array) {
		for (int i = 1; i < array.length; i++) {
			T key = array[i];
			int j = i - 1;

			while (j >= 0 && array[j].compareTo(key) > 0) {
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = key;
		}
	}
}
