package edu.neu.coe.info6205.sort.par;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class bottomUpSortDriver {
	
	static final int THREAD_COUNT = 8;
	static final int MIN_ARRAY_SIZE = 1048576;
	static final int MAX_ARRAY_SIZE = 16777216;
	static final int MIN_CUTOFF = 16384;
	static final int MAX_CUTOFF = 134217728;

	public static void main(String[] args) {
		
		Random random = new Random();
		
		try {
			FileWriter writer = new FileWriter("results/parsort/data3.csv",false);
			writer.write("array_size,thread_count,cutoff,time_taken\n");
			
			for(int size=MIN_ARRAY_SIZE;size<=MAX_ARRAY_SIZE;size*=2) {
				for(int cutoff=MIN_CUTOFF;cutoff<=MAX_CUTOFF;cutoff*=2) {
					if(cutoff>size) continue;
					System.out.println("sorting array of size "+size+" with cutoff "+cutoff);
					int[] arr = new int[size];
					for(int i=0;i<arr.length;i++) arr[i] = random.nextInt(100000);
					bottomUpPar sorter = new bottomUpPar(cutoff,arr,THREAD_COUNT);
					long startTime = System.currentTimeMillis();
					sorter.sort();
					long elapsedTime = System.currentTimeMillis()-startTime;
					
					writer.write(size+","+THREAD_COUNT+","+cutoff+","+elapsedTime+"\n");
				}
			}
			System.out.println("benchmark completed");
			writer.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
