package pl.put.poznan.sortingmadness.Logic.strategy;

public class MergeSortStrategy extends AbstractSortingStrategy {

	@Override
	public String getName() {
		return "Merge Sort";
	}

	@Override
	protected <T extends Comparable<T>> void sort(T[] array) {
		mergeSort(array, 0, array.length - 1);
	}

	private <T extends Comparable<T>> void mergeSort(T[] arr, int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;
			mergeSort(arr, left, mid);
			mergeSort(arr, mid + 1, right);
			merge(arr, left, mid, right);
		}
	}

	private <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
		T[] temp = (T[]) new Comparable[right - left + 1];
		int i = left, j = mid + 1, k = 0;

		while (i <= mid && j <= right) {
			temp[k++] = (arr[i].compareTo(arr[j]) <= 0) ? arr[i++] : arr[j++];
		}

		while (i <= mid)
			temp[k++] = arr[i++];
		while (j <= right)
			temp[k++] = arr[j++];

		System.arraycopy(temp, 0, arr, left, temp.length);
	}
}
