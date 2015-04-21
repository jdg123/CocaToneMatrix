

import java.awt.Dimension;

import javax.swing.JFrame;

import processing.core.*;

import org.openkinect.*;
import org.openkinect.processing.*;



public class KinectToMatrix extends PApplet{
	private static final long serialVersionUID = 1L;
	private static Kinect k;

	//Frames delay between updating the image
	private static final int TIME_DELAY = 5;


	private static final int TOTAL_WIDTH = 480;
	private static int rows = 8;
	private static int cols = 8;
	private static int leftOffset = 80;
	private static int cellWidth = TOTAL_WIDTH/cols;

	//
	private static float depthCutOff = 2f;
	private static float sensitivity = .5f;

	//This is the main output values of the class that stores the depth of any box on the grid.
	public static float[][] depth;

	private int[] rawDepth;
	private static float[][] defDepth;
	private float[] depthLookUp = new float[2048];
	private int c = 0;

	private Grid grid;
	public float minDist = .5f;
	public float lowToMid = 2.5f;
	public float midToHigh = 4.5f;

	public float percent_of_cell_cutoff = .4f;

	public KinectToMatrix(Grid g){
		grid = g;
	}

	public void setup() {
		size(TOTAL_WIDTH,TOTAL_WIDTH);
		background(0);
		k = new Kinect(this);
		if(k == null)return;
		k.start();
		if(k==null) return;
		k.enableDepth(true);
		k.enableRGB(true);
		k.processDepthImage(false);

		depth = new float[rows][cols];
		defDepth = new float[rows][cols];	
		for (int i = 0; i < depthLookUp.length; i++) {
			depthLookUp[i] = rawDepthToMeters(i);
		} 

		calibrate();
		System.out.println("Setup complete");


	}

	public void draw() {
		c++;

		if(c % TIME_DELAY == 0){


			background(1);
			if(k == null) return;
			PImage img = k.getVideoImage();
			image(img, -leftOffset, 0);

			rawDepth = k.getRawDepth();
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++){

					float levels[] = countDiffLevel(i, j);
					
					if(levels[2] > percent_of_cell_cutoff){
						grid.toggleCell(j, i, 3);
						fill(0, 0, 255, 100);
					}else if(levels[1] > percent_of_cell_cutoff){
						grid.toggleCell(j,  i, 2);
						fill(0, 255, 0, 100);
					}else if(levels[0] > percent_of_cell_cutoff){
						grid.toggleCell(j, i, 1);
						fill(255, 0, 0, 100);
					}else{
						grid.toggleCell(j, i, 0);
						fill(0, 0, 0, 0);
					}
					//int d = cellWidth*cellWidth;
					//fill(levels[2]/d * 255, levels[1]/d * 255, levels[0]/d * 255, 20);

					
//					depth[i][j] = getAvgDepth(i, j);


					//V1
					
//					float diff = defDepth[i][j] - depth[i][j];
//
//					if(diff < minDist){	
//						grid.toggleCell(j, i, 0);
//						fill(0, 0, 0, 0);
//
//					}else if(diff <= lowToMid){
//						grid.toggleCell(j, i, 1);
//						fill(255, 0, 0, 100);
//
//					}else if (diff <= midToHigh){
//						grid.toggleCell(j,  i, 2);
//						fill(0, 255, 0, 100);
//
//					}else if (diff >= midToHigh){
//						grid.toggleCell(j, i, 3);
//						fill(0, 0, 255, 100);
//					}

					 
					//V2
//					fill(0, 0, depth[i][j]*10, 100);
//					if(depth[i][j] < minDist){
//						grid.toggleCell(j, i, 3);
//					}else if(depth[i][j] < midToHigh){
//						grid.toggleCell(j,  i, 2);
//					}else if(depth[i][j] < lowToMid){
//						grid.toggleCell(j, i, 1);
//					}else{
//						grid.toggleCell(j, i, 0);
//					}


					 
					rect(j*cellWidth, i*cellWidth, cellWidth, cellWidth);

				}
			}
		}
	}

	private float getAvgDepth(int r, int c) {
		float sum = 0;
		int num = 0;
		int depth2d[][] = new int[480][640];
		for(int i = 0; i < 480; i++){
			for(int j = 0; j < 640; j++){
				depth2d[i][j] = rawDepth[i*640 + j];
			}
		}
		for(int i = 0; i < cellWidth; i++){
			for(int j = 0; j < cellWidth; j++){
				
				int d = depth2d[r*cellWidth +i][leftOffset+ c*cellWidth + j];
				if(d > 0 && d < 2048){
					sum += 1.0*depthLookUp[d];
					num++;
				}
			}
		}
		float avg = (float) ((1.0*sum)/num);

		return avg;
	}

	private float[] countDiffLevel(int r, int c) {
		float num = 0;
		int counts[] = new int[3];
		int depth2d[][] = new int[480][640];
		for(int i = 0; i < 480; i++){
			for(int j = 0; j < 640; j++){
				depth2d[i][j] = rawDepth[i*640 + j];
			}
		}
		for(int i = 0; i < cellWidth; i++){
			for(int j = 0; j < cellWidth; j++){
				
				int d = depth2d[r*cellWidth + i][leftOffset + c*cellWidth + j];
				if(d > 0 && d < 2048){
					float diff = defDepth[r][c] - depthLookUp[d];

					//version 1
					//					if(diff > minDist){	
					//						counts[0]++;
					//					}
					//					if(diff > lowToMid){
					//						counts[1]++;
					//					}
					//					if (diff > midToHigh){
					//						counts[2]++;
					//					}

					//Version 2
					if (diff > midToHigh){
						counts[2]++;
					}	
					else if(diff > lowToMid){
						counts[1]++;
					}
					else if(diff > minDist){	
						counts[0]++;
					}

					num++;

				}
			}
		}
		float results[] = { 1f*counts[0]/num, 1f*counts[1]/num, 1f*counts[2]/num };
//		System.out.println(results[0] + " " + results[1] + " " + results[2]);
		return results;
	}


	public void keyPressed() {
		if(key == 'a'){
			sensitivity += .05;
		}else if(key == 'z'){
			sensitivity -= .05;
		}else if(key == 'r'){
			calibrate();
		}
		System.out.println(sensitivity);
	}

	public void calibrate() {
		//System.out.println("calibrating");
		rawDepth = k.getRawDepth();		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				defDepth[i][j] = getAvgDepth(i, j);
			}
		}

	}

	public void stop() {
		k.quit();
	}


	float rawDepthToMeters(int depthValue) {
		if (depthValue < 2047) {
			return (float)(1.0 / ((double)(depthValue) * -0.0030711016 + 3.3309495161));
		}
		return 0.0f;
	}





}
