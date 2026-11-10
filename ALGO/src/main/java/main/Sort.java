package main;

public class Sort {
	public void swap(double[] data, int i, int j) {
		double temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	public void bubble(double[] data) {
		for (int i = 0; i < data.length - 1; i++)
			for (int j = 0; j < data.length - i - 1; j++)
				if (data[j] > data[j + 1])
					swap(data, j, j + 1);
	}
	
	public void bubbleOPT(double[] data) {
		for (int i = 0; i < data.length - 1; i++) {
			boolean swapped = false;
			for (int j = 0; j < data.length - i - 1; j++)
				if (data[j] > data[j + 1]) {
					swap(data, j, j + 1);
					swapped = true;
				}
			if (!swapped) break;
		}
	}

	public void selection(double[] data) {
		for (int i = 0; i < data.length; i++) {
			int min = i;
			for (int j = i + 1; j < data.length; j++)
				if (data[j] < data[min])
					min = j;
			swap(data, i, min);
		}
	}

	public void insertion(double[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int hole = i;
			double elem = arr[i];
			while (hole > 0 && arr[hole - 1] > elem) {
				arr[hole] = arr[hole - 1];
				hole--;
			}
			arr[hole] = elem;
		}
	}

	public void merge(double[] arr, double[] buf, int low, int mid, int high) {
		int i = low;
		int j = mid + 1;
		int k = low;
		while (i <= mid && j <= high) {
			if (arr[i] < arr[j])
				buf[k++] = arr[i++];
			else buf[k++] = arr[j++];
		}
		while (i <= mid)
			buf[k++] = arr[i++];
		while (j <= high)
			buf[k++] = arr[j++];
		for (i = low; i <= high; i++)
			arr[i] = buf[i];
	}

	public void mergeSort(double[] arr, double[] buf, int low, int high) {
		if (low < high) {
			int mid = (low + high) >> 1;
			mergeSort(arr, buf, low, mid);
			mergeSort(arr, buf, mid + 1, high);
			merge(arr, buf, low, mid, high);
		}
	}
	
	public void mergeSort(double[] arr) {
		var buf = new double[arr.length];
		mergeSort(arr, buf, 0, arr.length - 1);
	}

	public int partition(double[] arr, int low, int high) {
		double pivot = arr[high];
		int p = low;
		for (int i = low; i < high; i++)
			if (arr[i] <= pivot) {
				swap(arr, i, p);
				p++;
			}
		swap(arr, p, high);
		return p;
	}

	public void quickSort(double[] arr, int low, int high) {
		if (low < high) {
			int p = partition(arr, low, high);
			quickSort(arr, low, p - 1);
			quickSort(arr, p + 1, high);
		}
	}
	
	public void quickSort(double[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	public static void main(String[] args) {
		final int N = 100_000;
		double[] data = new double[N];
		for (int a = 0; a < data.length; a++)
			data[a] = Math.random() * N;
		var sort = new Sort();
		long start = System.currentTimeMillis();
		sort.insertion(data);
		long end = System.currentTimeMillis();
		Console.draw("Total time elapsed: %f seconds.\n", (end - start) / 1000.0);
	}
}