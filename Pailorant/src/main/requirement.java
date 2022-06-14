package main;

import java.util.ArrayList;

public class requirement {
	
	public static int binarySearch(int arr[], int x)
    {
 
        int l = 0, r = arr.length - 1;
 
        while (l <= r) {
            int m = l + (r - l) / 2;
 
            if (arr[m] == x)
                return m;
 
            if (arr[m] < x)
                l = m + 1;
            else
                r = m - 1;
        }
        return -1;
    }
	
	public static int binarySearch(ArrayList<Integer> arr, int x)
    {
 
        int l = 0, r = arr.size() - 1;
 
        while (l <= r) {
            int m = l + (r - l) / 2;
 
            if (arr.get(m) == x)
                return m;
 
            if (arr.get(m) < x)
                l = m + 1;
            else
                r = m - 1;
        }
        return -1;
    }
	
	public static void mergeSort(int[] a, int n) {
	    if (n < 2) {
	        return;
	    }
	    int mid = n / 2;
	    int[] l = new int[mid];
	    int[] r = new int[n - mid];

	    for (int i = 0; i < mid; i++) {
	        l[i] = a[i];
	    }
	    for (int i = mid; i < n; i++) {
	        r[i - mid] = a[i];
	    }
	    mergeSort(l, mid);
	    mergeSort(r, n - mid);

	    merge(a, l, r, mid, n - mid);
	}
	
	public static void merge(int[] a, int[] l, int[] r, int left, int right) {
			 
			    int i = 0, j = 0, k = 0;
			    while (i < left && j < right) {
			        if (l[i] <= r[j]) {
			            a[k++] = l[i++];
			        }
			        else {
			            a[k++] = r[j++];
			        }
			    }
			    while (i < left) {
			        a[k++] = l[i++];
			    }
			    while (j < right) {
			        a[k++] = r[j++];
			    }
			}
}
