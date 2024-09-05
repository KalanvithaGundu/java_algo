package sorting;

import java.util.List;
import java.util.ArrayList;

public class SortingAlgo {


	public static int[] randomArray(int size){
		int[] arr = new int[size];
		for(int i=0; i<size; i++)
			arr[i] = (int)((Math.random()-0.5)*1000) %100;
		return arr;
	}

	public static void print(int[] array){
		System.out.print("[ ");
		for(int a: array)
			System.out.print(a+", ");
		System.out.println("]");
	}

	// Swapping two elements
	static void swap(int[] array, int a, int b){
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	//-------------------------- Sorting algorithms

	// Insertion sort
	public static int[] insertionSort(int[] array){
		for (int i=1; i<array.length; i++) {
			for (int j=0; j<i; j++) {
				if(array[i] < array[j]){
					for(int k = i; k>j; k--){
						swap(array, k, k-1);
					}
					break;
				}
			}
		}
		return array;
	}

	// Selection sort
	public static int[] selectionSort(int[] array){
		int min;
		for (int i=0; i < array.length; i++) {
			min = i;
			for (int j=i; j<array.length; j++) {
				if(array[min] > array[j])
					min = j;
			}
			swap(array, i, min);
		}
		return array;
	}

	// Shell sort
	public static int[] shellSort(int[] array){
		for (int offset = array.length /2; offset > 0; offset /= 2) {
			for (int i = offset; i < array.length; i++) {
				int index = i;
				for (int j = i - offset; j>=0; j-= offset) {
					if(array[j] > array[index]){
						swap(array, j, index);
						index = j;
					}
				}
			}
		}
		return array;
	}

	// Merge sort
	public static int[] mergeSort(int[] array){
		int[][] s= split(array);
		array = merge(s[0], s[1]);
		return array;
	}

	private static int[][] split(int[] a){
		int size = a.length /2;
		int[][] s = new int[2][];
		s[0] = new int[size];
		s[1] = new int[a.length - size];
		System.arraycopy(a,0,s[0],0,size);
		System.arraycopy(a,size,s[1],0,a.length -size);
		return s;
	}

	private static int[] merge(int[] a, int[] b){
		if(a.length > 1){
			int[][] s = split(a);
			a = merge(s[0], s[1]);
		}
		if(b.length > 1){
			int[][] s = split(b);
			b = merge(s[0],s[1]);
		}

		int ai= 0, bi= 0;
		int[] m = new int[a.length + b.length];
		for (int i=0; i<m.length; i++) {
			if(ai<a.length && bi<b.length){
				if(a[ai] > b[bi])
					m[i] = b[bi++];
				else
					m[i] = a[ai++];
			}
			else if(ai < a.length){
				m[i] = a[ai++];
			}
			else if(bi < b.length){
				m[i] = b[bi++];
			}
		}
		return m;
	}

	// Quick sort
	public static int[] quickSort(int[] array){
		return quickSort(array, 0, array.length -1);
	}

	private static int[] quickSort(int[] array, int start, int end){
		if (end - start <= 1) {
			if((end-start ==1) && array[start] > array[end]){
				swap(array, start, end);
			}
			return array;
		}

		int pivot = pivot(array, start);
		int med = partition(array, start, end, pivot);
		quickSort(array, start, med-1);
		quickSort(array, med+1, end);
		return array;
	}

	private static int pivot(int[] array, int start){
		return start;
	}

	private static int partition(int[] array, int start, int end, int pivot){
		swap(array, end, pivot);
		int place = end;

		for (int i = start; i< end; i++) {
			if(array[i] > array[end]){
				boolean ordered = true;
				for (int j = i+1; j<end; j++) {
					if(array[j]<=array[end]){
						ordered = false;
						swap(array, j, i);
						break;
					}
				}
				if(ordered){
					place = i;
					break;
				}
			}
		}
		swap(array, place, end);
		return place;
	}

	// Heap sort
	public static int[] heapSort(int[] array){
		int hsize;
		for(hsize = 1; hsize < array.length; hsize++){
			heapfyUp(array, hsize, hsize);
		}
		while(--hsize != 0){
			swap(array, 0, hsize);
			heapfyDown(array, hsize -1, 0);
		}
		return array;
	}

	private static void heapfyDown(int[] array, int hsize, int index){
		if(index*2 + 1 <= hsize){
			int child = index*2 + 1;
			if(index*2 +2 <= hsize && array[child] < array[index*2+2])
				child = index*2 + 2;
			if(array[child]>array[index]){
				swap(array, index, child);
				heapfyDown(array, hsize, child);
			}
		}
	}

	private static void heapfyUp(int[] array, int hsize, int index){
		int parent = (index %2 ==0)?((index-2)/2):((index-1)/2);
		if(parent >= 0){
			if(array[index] > array[parent]){
				swap(array, index, parent);
				heapfyUp(array, hsize, parent);
			}
		}
	}

	// Radix sort
	public static int[] radixSort(int[] array){
		List<List<Integer>> buckets = new ArrayList<List<Integer>>();
		for(int i=0; i<10*2; i++)
			buckets.add(new ArrayList<Integer>());
		int index = 0;
		do{
			for(List<Integer> bucket: buckets)
				bucket.clear();
			for (int i=0; i<array.length; i++) {
				int value = array[i];
				int bucketIndex = getDigit(value, index) +10;
				buckets.get(bucketIndex).add(value);
			}
			if(buckets.get(10).size() == array.length)
				break;
			List<Integer> bucket = buckets.get(0);
			int bucketIndex = 0;
			for (int i=0; i<array.length; i++) {
				while(bucket.isEmpty())
					bucket = buckets.get(++bucketIndex);
				array[i] = bucket.remove(0);
			}
			index++;
		} while(true);
		return array;
	}

	private static int getDigit(int value, int index){
		long exp = 1;
		for(int i=0;i<index;i++)
			exp*=10;
		return ((int)((double)value/exp))%10;
	}


	public static void main(String[] args) {
		int input_size = 15;
		int[] array_input = randomArray(input_size);
		int[] array = new int[input_size];

		System.arraycopy(array_input, 0, array, 0, input_size);
		System.out.println("Insertion:");
		print(array);
		print(insertionSort(array));

		System.arraycopy(array_input, 0, array, 0, input_size);
		System.out.println("Selection:");
		print(array);
		print(selectionSort(array));

		System.arraycopy(array_input, 0, array, 0, input_size);
		System.out.println("Shell:");
		print(array);
		print(shellSort(array));

		System.arraycopy(array_input, 0, array, 0, input_size);
		System.out.println("Merge:");
		print(array);
		print(mergeSort(array));

		System.arraycopy(array_input, 0, array, 0, input_size);
		System.out.println("Quick:");
		print(array);
		print(quickSort(array));

		System.arraycopy(array_input, 0, array, 0, input_size);
		System.out.println("Heap:");
		print(array);
		print(heapSort(array));

		System.arraycopy(array_input, 0, array, 0, input_size);
		System.out.println("Radix:");
		print(array);
		print(radixSort(array));

		System.out.println("---- Profiling ----");
		profiling();
	}

	public static void profiling(){
		int[] loads            = {10000, 20000, 40000};
		String[] algorithmName = {"Insertion", "Selection", "Shell", "Merge", "Quick", "Heap", "Radix"};
		long[][] results = new long[algorithmName.length][loads.length];
		for(int i = 0; i < loads.length; i++){
			long time;
			int[] array_input = randomArray(loads[i]);
			int[] array = new int[loads[i]];

			System.arraycopy(array_input, 0, array, 0, loads[i]);
			time = System.currentTimeMillis();
			insertionSort(array);
			results[0][i] = System.currentTimeMillis() - time;

			System.arraycopy(array_input, 0, array, 0, loads[i]);
			time = System.currentTimeMillis();
			selectionSort(array);
			results[1][i] = System.currentTimeMillis() - time;

			System.arraycopy(array_input, 0, array, 0, loads[i]);
			time = System.currentTimeMillis();
			shellSort(array);
			results[2][i] = System.currentTimeMillis() - time;

			System.arraycopy(array_input, 0, array, 0, loads[i]);
			time = System.currentTimeMillis();
			mergeSort(array);
			results[3][i] = System.currentTimeMillis() - time;

			System.arraycopy(array_input, 0, array, 0, loads[i]);
			time = System.currentTimeMillis();
			quickSort(array);
			results[4][i] = System.currentTimeMillis() - time;

			System.arraycopy(array_input, 0, array, 0, loads[i]);
			time = System.currentTimeMillis();
			heapSort(array);
			results[5][i] = System.currentTimeMillis() - time;

			System.arraycopy(array_input, 0, array, 0, loads[i]);
			time = System.currentTimeMillis();
			radixSort(array);
			results[6][i] = System.currentTimeMillis() - time;
		}

		for(int n = 0; n < algorithmName.length; n++){
			System.out.printf("%-10s: ", algorithmName[n]);
			for(int i = 0; i < loads.length; i++)
				System.out.printf(" %03.3fs", results[n][i]/1000.0);
			System.out.println();
		}
	}
}