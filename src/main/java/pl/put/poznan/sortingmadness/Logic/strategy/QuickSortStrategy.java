package pl.put.poznan.sortingmadness.Logic.strategy;

public class QuickSortStrategy extends AbstractSortingStrategy {

	@Override
	public String getName() {
		return "Quick Sort";
	}

	@Override
	protected <T extends Comparable<T>> void sort(T[] array) {
		quickSort(array, 0, array.length - 1);
	}

	private <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
		if (low < high) {
			int pi = partition(arr, low, high);
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}

	private <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
		T pivot = arr[high];
		int i = low - 1;

		for (int j = low; j < high; j++) {
			if (arr[j].compareTo(pivot) < 0) {
				i++;
				T tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
		}

		T tmp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = tmp;

		return i + 1;
	}
}
