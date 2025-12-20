package pl.put.poznan.sortingmadness.Logic.strategy;

public class HeapSortStrategy extends AbstractSortingStrategy {

	@Override
	public String getName() {
		return "Heap Sort";
	}

	@Override
	protected <T extends Comparable<T>> void sort(T[] array) {
		int n = array.length;

		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(array, n, i);
		}

		for (int i = n - 1; i > 0; i--) {
			T tmp = array[0];
			array[0] = array[i];
			array[i] = tmp;
			heapify(array, i, 0);
		}
	}

	private <T extends Comparable<T>> void heapify(T[] arr, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;

		if (l < n && arr[l].compareTo(arr[largest]) > 0)
			largest = l;
		if (r < n && arr[r].compareTo(arr[largest]) > 0)
			largest = r;

		if (largest != i) {
			T swap = arr[i];
			arr[i] = arr[largest];
			arr[largest] = swap;
			heapify(arr, n, largest);
		}
	}
}
