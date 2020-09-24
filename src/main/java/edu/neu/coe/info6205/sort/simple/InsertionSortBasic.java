package edu.neu.coe.info6205.sort.simple;

import java.util.Arrays;

/**
 * This is a basic implementation of insertion sort.
 * It does not extend Sort, nor does it employ any optimizations.
 */
public class InsertionSortBasic {

    public void sort(Object[] a) {
        for (int i = 1; i < a.length; i++) swap(i, a);
    }

    private void swap(int i, Object[] a) {
        for(int j=i;j>0;j--) {
        	if((int)a[j-1]>(int)a[j]) swap(a,j,j-1);
        }
    }

    private void swap(Object[] a, int j, int i) {
        Object temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public static void main(String[] args) {
        InsertionSortBasic sorter = new InsertionSortBasic();
        Object[] inp = {3,2,1,3,1,6,1,1,5,8,1,6,67,9675,2,4};
        System.out.println(Arrays.toString(inp));
        sorter.sort(inp);
        System.out.println(Arrays.toString(inp));
    }
}
