/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RandomWalk {

    private int x = 0;
    private int y = 0;
    
    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
    	x = x+dx;
    	y = y+dy;
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        for(int i=0;i<m;i++){
            randomMove();
            //System.out.println("x: "+x+" y: "+y);
        }
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        return Math.sqrt((x*x)+(y*y));
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n,FileWriter writer) {
    	double distance = 0;
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            distance = walk.distance();
            totalDistance = totalDistance + distance;      
            try {
            	writer.write(m+","+i+","+distance+"\n");
              } 
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
        }
        return totalDistance / n;
    }  

    public static void main(String[] args) {
//        if (args.length == 0)
//            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
//        int m = Integer.parseInt(args[0]);
    	
        int n = 10000;
        double meanDistance = 0;
        if (args.length > 1) n = Integer.parseInt(args[1]);
        
        //Creates a new csv file with the current timestamp, then calls the randomWalkMulti method for m=0 to m=30
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
		String timestamp = LocalTime.now().format(formatter);
        try {
			FileWriter writer = new FileWriter("data_"+timestamp+".csv",true);
			writer.write("step_count,run,distance\n");
	        for(int m=0;m<30;m++) {
	            meanDistance = randomWalkMulti(m, n,writer);
	            System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");	
	        }
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
