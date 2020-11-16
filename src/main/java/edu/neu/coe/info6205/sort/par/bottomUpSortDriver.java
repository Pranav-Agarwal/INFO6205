package edu.neu.coe.info6205.sort.par;

import java.util.Random;

public class bottomUpSortDriver {
	
	private static boolean isSorted(int[] arr ) {
		for(int i=1;i<arr.length;i++) {
			if(arr[i]<arr[i-1]) return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Random random = new Random();
		int[] arr = new int[10000000];
		for(int i=0;i<arr.length;i++) arr[i] = random.nextInt(10000);
		bottomUpPar sorter = new bottomUpPar(10000,arr,2);
		long startTime = System.currentTimeMillis();
		try {
			sorter.sort();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		//System.out.println(Arrays.toString(arr));
		System.out.println("Time taken: "+(endTime-startTime)+ "ms, Sorted: "+isSorted(arr));
	}

}
