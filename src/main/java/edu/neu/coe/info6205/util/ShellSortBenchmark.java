package edu.neu.coe.info6205.util;
import java.io.FileWriter;
import java.io.IOException;
import edu.neu.coe.info6205.sort.simple.ShellSort;

public class ShellSortBenchmark {
	
	private static final int N = 14641;
	private static final int MIN_VALUE = -99999;
	private static final int MAX_VALUE = 99999;
	private static final int GAP_SEQUENCE = 3;  //1 for insertion sort, 2 or 3 for shell sort
	
	private static int getInversions(Integer[] inp) {
		int ans=0;
		for(int i=0;i<inp.length-1;i++) {
			for(int j=i+1;j<inp.length;j++) {
				if(inp[j]<inp[i]) ans++;
			}
		}
		return ans;
	}
	
    public static void main(String args[]) {
		Integer[] xs = new Integer[N];
		for(int i=0;i<N;i++) {
			xs[i] = (int) (MIN_VALUE + ((MAX_VALUE-MIN_VALUE)*Math.random()));
		}
        ShellSort<Integer> s = new ShellSort<Integer>(GAP_SEQUENCE,N);
        int from = 0;
        int to = N;
        s.first();
        long inversions = getInversions(xs);
        long initial_inversions = inversions;
        long fixes = 0;
        double fixesRatio;
        System.out.println("unsorted: inversions = "+inversions);

    	try {
			FileWriter writer = new FileWriter("results/shell_sort/data_3.csv");
			writer.write("pass,h,inversions_before_pass,inversions,swaps,fixes,average fixes/swap,ratio of inital inversions fixed,ratio of current inversions fixed\n");
			int pass=0;
	        while (s.h > 0) {
	            s.hSort(s.h, xs, from, to);
	            int temp = getInversions(xs);
	            pass++;
	            writer.write(pass+",");
	            writer.write(s.h+",");
	            writer.write(inversions+",");
	            fixes=inversions-temp;
	            fixesRatio = fixes/(double)inversions;
	            System.out.println(fixes+" "+inversions+" "+temp+" "+fixesRatio);
	            inversions = temp;
	            writer.write(inversions+",");
	            writer.write(s.swaps+",");
	            writer.write(fixes+",");
	            writer.write(fixes/(double)s.swaps+",");
	            writer.write(fixes/(double)initial_inversions+",");
	            writer.write(fixesRatio+"\n");
	            //System.out.println("h = "+ s.h +": swaps/compares = "+s.swaps + " inversions = "+inversions+" fixes = "+fixes+" fixesRatio = "+fixesRatio);
	            s.h = s.next();
	        }
			writer.close();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
        //System.out.println(Arrays.toString(xs));
    }


}

