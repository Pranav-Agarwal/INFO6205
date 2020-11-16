package edu.neu.coe.info6205.sort.simple;

import java.util.Arrays;

public class shellSortBenchmark {
	
	private static int getInversions(Integer[] inp) {
		int ans=0;
		for(int i=0;i<inp.length-1;i++) {
			for(int j=i+1;j<inp.length;j++) {
				if(j<i) ans++;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		ShellSort<Integer> s = new ShellSort<>(3);
		int n = 12413;
		int max = 99999;
		int min = -99999;
		Integer[] random = new Integer[n];
		for(int i=0;i<n;i++) {
			random[i] = (int) (min + ((max-min)*Math.random()));
		}
		s.sort(random,0,n);
		System.out.println(getInversions(random));
		System.out.println(Arrays.toString(random));
	}

}
