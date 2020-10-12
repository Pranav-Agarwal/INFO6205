package edu.neu.coe.info6205.union_find;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Assignment3 {
	
	private static final int VALUES_OF_N = 10;
	private static final int RUNS_PER_N = 10;
	private static final int INITIAL_N = 64;
	
	public Assignment3() {
		
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
     * @return count the number of random pairs needed to connect all nodes.
     */
	private static int count(int n, int runs) {
		int size = n;
		double av = 0;
		for(int i=0;i<runs;i++) {
			UF h = new UF_HWQUPC(size, true);
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
	
	public static void main (String[] args) {

    	try {
			FileWriter writer = new FileWriter("results/union_find/data.csv");
			writer.write("n,pairs\n");

			for(int i=0;i<VALUES_OF_N;i++) {
				System.out.println(i);
				int t = (int)Math.pow(2, i);
				t = t*INITIAL_N;
				writer.write(t+",");
				writer.write(count(t,RUNS_PER_N)+"\n");
			}
			writer.close();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
	}
}
