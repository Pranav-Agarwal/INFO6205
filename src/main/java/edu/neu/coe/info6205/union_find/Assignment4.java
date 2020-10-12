package edu.neu.coe.info6205.union_find;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import edu.neu.coe.info6205.util.Benchmark_Timer;

public class Assignment4 {
	
	private static final int VALUES_OF_N = 15;
	private static final int RUNS_PER_N = 10;
	private static final int INITIAL_N = 64;
	
	public Assignment4() {
		
	}
	
    /**
     * Returns a random pair of integers.
     *
     * @param n the upper limit to generate
     * @return returns an int[2] with random ints.
     */
	private static int[] randomPair(int n) {
		int[] ans = new int[2];
		ans[0] = (int)(Math.random()*n);
		ans[1] = (int)(Math.random()*n);
		//if(ans[0]==ans[1]) ans = randomPair(n);
		return ans;
	}
	
    /**
     * Returns the number of random pairs needed to connect all nodes.
     *
     * @param n the number of nodes
     * @param runs the number of runs per n that is averaged
     * @param pathCompression whether to enable path halving
     * @return count the number of random pairs needed to connect all nodes.
     */
	private static int count(int n, int runs,int pathCompression) {
		int size = n;
		double av = 0;
		for(int i=0;i<runs;i++) {
			UF h = null;
			if (pathCompression==0) h = new UF_HWQUPC(size,false);
			if (pathCompression==1) h = new UF_HWQUPC(size,true);
			if (pathCompression==2) h = new WQUPC(size);
			int pairs=0;
			while(h.components()>1) {
				int[] temp = randomPair(size);
				h.connect(temp[0], temp[1]);
				pairs++;
			}
			av+= pairs;
		}
		return (int)(av/runs);
	}
	
    /**
     * Performs union find on array of size n and finds average distance to root after.
     *
     * @param n the number of nodes
     * @param pathCompression whether to enable path halving
     * @return the average distance to root over all nodes.
     */
	private static double[] unionFindStats(int n,int pathCompression) {
		int size = n;
		double[] ans = new double[2];
		double depth = 0;
		double pairs = 0;
		for(int i=0;i<RUNS_PER_N;i++) {
			UF h = null;
			if (pathCompression==0) h = new UF_HWQUPC(size,false);
			if (pathCompression==1) h = new UF_HWQUPC(size,true);
			if (pathCompression==2) h = new WQUPC(size);
			while(h.components()>1) {
				int[] temp = randomPair(size);
				h.connect(temp[0], temp[1]);
				pairs++;
			}
			if(pathCompression==2) depth +=((WQUPC) h).averageDepth();
			else depth +=((UF_HWQUPC) h).averageDepth();
		}
		ans[0] = depth/RUNS_PER_N;
		ans[1] = pairs/RUNS_PER_N;
		return ans;
	}
	
	public static void main (String[] args) {
		
		//pre function unused
    	UnaryOperator<Integer> pre = inp -> inp;
    	
    	//function to be benchmarked
    	Consumer<Integer> func1 = inp -> count(inp,1,0);
    	Consumer<Integer> func2 = inp -> count(inp,1,1);
    	Consumer<Integer> func3 = inp -> count(inp,1,2);
    	
    	//post function unused
    	Consumer<Integer> post = inp -> System.out.print("");
    	
    	Benchmark_Timer<Integer> t1 = new Benchmark_Timer<>("WQU Benchmark",pre,func1,post);   //no compression
    	Benchmark_Timer<Integer> t2 = new Benchmark_Timer<>("WQUPH Benchmark",pre,func2,post); //path halving
    	Benchmark_Timer<Integer> t3 = new Benchmark_Timer<>("WQUPC Benchmark",pre,func3,post); //path compression

    	try {
			FileWriter writer = new FileWriter("results/union_find/data1.csv");
			writer.write("n,uncompressed time,uncompressed av depth,uncompressed pairs, path-halved time,path-halved av depth,path-halved pairs,compressed time,compressed av depth,compressed pairs,\n");

			for(int i=0;i<VALUES_OF_N;i++) {
				System.out.println(i);
				int k = (int)Math.pow(2, i);
				k = k*INITIAL_N;
				double[] temp;
				writer.write(k+",");
				writer.write(t1.run(k,RUNS_PER_N)+",");
				temp = unionFindStats(k,0);
				writer.write(temp[0]+",");
				writer.write(temp[1]+",");
				writer.write(t2.run(k,RUNS_PER_N)+",");
				temp = unionFindStats(k,1);
				writer.write(temp[0]+",");
				writer.write(temp[1]+",");
				writer.write(t3.run(k,RUNS_PER_N)+",");
				temp = unionFindStats(k,2);
				writer.write(temp[0]+",");
				writer.write(temp[1]+"\n");
			}
			writer.close();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
	}
}
