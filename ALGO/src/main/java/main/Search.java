package main;

public class Search {
	public boolean linearSearch(int[] data, int key) {
		for (int i = 0; i < data.length; i++)
			if (data[i] == key)
				return true;
		return false;
	}

	public boolean binarySearch(int[] data, int key) {
		int low = 0;
		int high = data.length - 1;
		while (low < high) {
			int mid = (low + high) >> 1;
			if (key < data[mid]) high = mid - 1;
			else if (key > data[mid]) low = mid + 1;
			return true;
		}
		return false;
	}

	public int lonelySearch(int[] data) {
		int lonely = 0;
		for (int i = 0; i < data.length; i++)
			lonely = lonely ^ data[i];
		return lonely;
	}

	public int findFirstOccurance(int[] data, int key) {
		int first = -1;
		int low = 0;
		int high = data.length - 1;
		while (low <= high) {
			int mid = (low + high) >> 1;
			if (key < data[mid]) high = mid - 1;
			else if (key > data[mid]) low = mid + 1;
			else {
				first = mid;
				high = mid - 1;
			}
		}
		return first;
	}

	public int findLastOccurance(int[] data, int key) {
		int last = -1;
		int low = 0;
		int high = data.length - 1;
		while (low <= high) {
			int mid = (low + high) >> 1;
			if (key < data[mid]) high = mid - 1;
			else if (key > data[mid]) low = mid + 1;
			else {
				last = mid;
				low = mid + 1;
			} 
		}
		return last;
	}

	public int findOccuranceCount(int[] data, int key) {
		int first = findFirstOccurance(data, key);
		int last = findLastOccurance(data, key);
		return last - first + 1;
	}
}