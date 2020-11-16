package edu.neu.coe.info6205.sort.par;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class bottomUpPar{
	
	public int cutoff;
	public int[] arr;
	public ExecutorService pool;
	
	public bottomUpPar(int cutoff, int[] arr, int poolSize) {
		this.cutoff = cutoff;
		this.arr = arr;
		pool = Executors.newFixedThreadPool(poolSize);
	}
	
	private void merge(int from,int mid,int to) {
		if(mid>=to) return;
		if(arr[mid]>=arr[mid-1]) return;
		//System.out.println(Arrays.toString(Arrays.copyOfRange(arr, from, to+1)));
		int[] temp = new int[to-from+1];
		//System.out.print("merging from "+from+" to "+to+" on Thread "+Thread.currentThread().getName()+" with l "+from+" m "+mid+" r "+to+"---");
		for(int i=from;i<=to;i++) {
			//System.out.print(" "+i);
			temp[i-from] = arr[i];
		}
		//System.out.println("");
		int l = 0;
		int m = mid-from;
		int r = m;
		int counter=from;
		while(l<m || r<temp.length) {
			//System.out.println("----- merging  "+l+" to "+r+" on Thread "+Thread.currentThread().getName());
			if(l>=m) arr[counter++] = temp[r++];
			else if(r>=temp.length) arr[counter++] = temp[l++];
			else if(temp[l]<=temp[r]) arr[counter++] = temp[l++];
			else arr[counter++] = temp[r++];
		}
		//System.out.println("merged from "+from+" to "+to+" on Thread "+Thread.currentThread().getName()+"--");
		//System.out.println(Arrays.toString(Arrays.copyOfRange(arr, from, to+1)));
	}
	
	private void sort(int from,int to) {
		Arrays.sort(arr, from, to);
		//System.out.println("sorted from "+from+" to "+(to-1)+" on Thread "+Thread.currentThread().getName());
	}
	
	
	public void sort() throws InterruptedException {
		int sort_segment_start = 0;
		List<Callable<Void>> tasks = new ArrayList<>();
		//INITIAL SORTING OF CUTOFF SIZED SEGMENTS USING SYSTEM SORT
		while(sort_segment_start<arr.length) {
			final int start = sort_segment_start;
			final int end = Math.min(sort_segment_start+cutoff,arr.length);
			tasks.add(new Callable<Void>() {
				public Void call() {
					sort(start,end);
					return null;
				}
			});
			sort_segment_start+=cutoff;
		}
		pool.invokeAll(tasks);
		
		//MERGING SORTED SEGMENTS
		int size = cutoff;
		while(size<=arr.length) {
			//System.out.println(size);
			int merge_segment_start = 0;
			while(merge_segment_start<arr.length) {
				//System.out.println(merge_segment_start);
				final int start = merge_segment_start;
				final int mid = Math.min(start+size,arr.length-1);
				final int end = Math.min(start+(size*2)-1,arr.length-1);
				tasks.add(new Callable<Void>() {
					public Void call() {
						merge(start,mid,end);
						return null;
					}
				});
				merge_segment_start+=size*2;
			}
			pool.invokeAll(tasks);
			size*=2;
		}
		pool.shutdown();

	}
}