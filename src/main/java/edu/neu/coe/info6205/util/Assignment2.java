package edu.neu.coe.info6205.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import edu.neu.coe.info6205.sort.simple.InsertionSort;

public class Assignment2 {
	
	private static final int INITAL_SIZE = 100;  //Inital value of N to test for
	private static final int DOUBLING_LIMIT = 7;  //Number of values of N to test for, doubling every time
	private static final int RUNS = 10;  //Number of runs for a given N and array input.
	private static final int MAX = -10000; //max value of an integer in the array
	private static final int MIN = 10000;  //min value of an integer in the array

	private static ArrayList<Integer[]> arrayGen(int n,int max,int min){
		ArrayList<Integer[]> res = new ArrayList<Integer[]>();
		Integer[] random = new Integer[n];
		for(int i=0;i<n;i++) {
			random[i] = (int) (min + ((max-min)*Math.random()));
		}
		Integer[] nor_sorted = random.clone();
		Collections.sort(Arrays.asList(nor_sorted));
		
		Integer[] rev_sorted = nor_sorted.clone();
		Collections.reverse(Arrays.asList(rev_sorted));
		
		//Partially sorted array is generated by sorting the first 90% of the array
		Integer[] par_sorted = random.clone();
		new InsertionSort<Integer>().sort(par_sorted,0,(int) (n*0.9));
		
		res.add(random);
		res.add(nor_sorted);
		res.add(rev_sorted);
		res.add(par_sorted);
		
		return res;
	}

	public static void main(String[] args) {
		
    	UnaryOperator<Integer[]> pre = inp -> inp;
    	Consumer<Integer[]> func = inp -> new InsertionSort<Integer>().sort(inp,true);
    	Consumer<Integer[]> post = inp -> System.out.print("");
    	Benchmark_Timer<Integer[]> t = new Benchmark_Timer<Integer[]>("Insertion Sort Benchmark",pre,func,post);
    	
    	try {
			FileWriter writer = new FileWriter("results/insertion_sort_benchmark/data.csv");
			writer.write("n,random,sorted,reverse_sorted,partially_sorted\n");
			for(int size=INITAL_SIZE;size<=INITAL_SIZE*Math.pow(2, DOUBLING_LIMIT);size=size*2) {
		    	ArrayList<Integer[]> inputs = arrayGen(size,MAX,MIN);
		    	writer.write(size+",");
		    	System.out.println("Testing for n: "+size);
		    	writer.write(Double.toString(t.run(inputs.get(0), RUNS))+",");
		    	writer.write(Double.toString(t.run(inputs.get(1), RUNS))+",");
		    	writer.write(Double.toString(t.run(inputs.get(2), RUNS))+",");
		    	writer.write(Double.toString(t.run(inputs.get(3), RUNS))+",");
		    	writer.write('\n');
			}
			writer.close();
			System.out.println("Benchmark finished. Results in results/insertion_sort_benchmark/data.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}

}
