package com.squareshift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AirplaneSeatingEngine {

	static int noOfPassenger = 0;

	static int currSeat = 1;

	static List<Integer> rowSegment = new ArrayList<Integer>();

	static HashMap<Integer, Integer> colSegment = new HashMap<Integer, Integer>();

	static int totalAvailableSeat = 0;

	static int rowSize = 0;

	static int colSize = 0;

	static HashMap<String, Integer> availabiltyMap = new HashMap<String, Integer>();

	static int seg = 0;

	static boolean flag = false;

	public static void main(String args[]) {

		System.out.println("Airplane Seating Allocation");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		List<int[][]> seatList = new ArrayList<int[][]>();
		System.out.println("***************************************");
		System.out.println("Enter no of passengers :");
		noOfPassenger = sc.nextInt();
		System.out.println("Enter no of Segments :");
		int sizeOfArray = sc.nextInt();

		availabiltyMap.put("W", 0);
		availabiltyMap.put("M", 0);
		availabiltyMap.put("A", 0);

		for (int i = 0; i < sizeOfArray; i++) {
			System.out.println("Enter no of Segment row :");
			int rows = sc.nextInt();
			System.out.println("Enter no of Segment column :");
			int columns = sc.nextInt();

			int seats[][] = new int[rows][columns];
			rowSize += rows;
			rowSegment.add(rowSize);
			if (colSize <= columns) {
				colSize = columns;
			}
			colSegment.put(i, colSize);
			seatList.add(seats);
		}

		String[][] finalSeat = new String[rowSize][colSize];

		formSeat(finalSeat);
		if(totalAvailableSeat < noOfPassenger) {
			while (noOfPassenger != 0) {
				if (availabiltyMap.get("A") != 0) {
					allocateSeat("A", finalSeat, availabiltyMap, totalAvailableSeat);
				}
				if (availabiltyMap.get("W") != 0) {
					allocateSeat("W", finalSeat, availabiltyMap, totalAvailableSeat);
				}
				if (availabiltyMap.get("M") != 0) {
					allocateSeat("M", finalSeat, availabiltyMap, totalAvailableSeat);
				}
	
			}
		}

		printResult(finalSeat);

	}

	/**
	 * Print the passenger allocated Map
	 * 
	 * @param finalSeat
	 */
	private static void printResult(String[][] finalSeat) {
		for (int i = 0; i < finalSeat[i].length; i++) {
			for (int j = 0; j < finalSeat.length; j++) {
				if (colSegment.get(seg) <= i) {
					flag = true;
				} else {
					flag = false;
				}
				if (rowSegment.contains(j + 1)) {
					if (colSegment.size() - 1 == seg) {
						seg = 0;
						flag = false;
					} else {
						seg++;
					}
				}
				if (rowSegment.contains(j)) {
					System.out.print("        ");
				}

				if (flag) {
					System.out.print("      ");
				} else {
					if (j == 0 || j == rowSize - 1) {
						System.out.print(finalSeat[j][i]);
					} else if (rowSegment.contains(j) || rowSegment.contains(j + 1)) {
						System.out.print(finalSeat[j][i]);
					} else {
						System.out.print(finalSeat[j][i]);
					}
				}
			}
			System.out.println();
		}
	}

	private static void formSeat(String[][] finalSeat) {

		for (int i = 0; i < finalSeat[i].length; i++) {
			for (int j = 0; j < finalSeat.length; j++) {
				if (colSegment.get(seg) <= i) {
					flag = true;
				} else {
					flag = false;
				}
				if (rowSegment.contains(j + 1)) {
					if (colSegment.size() - 1 == seg) {
						seg = 0;
						flag = false;
					} else {
						seg++;
					}
				}
				if (!flag) {
					if (j == 0 || j == rowSize - 1) {
						finalSeat[j][i] = "W-XX ";
						availabiltyMap.put("W", availabiltyMap.get("W") + 1);
					} else if (rowSegment.contains(j) || rowSegment.contains(j + 1)) {
						availabiltyMap.put("A", availabiltyMap.get("A") + 1);
						finalSeat[j][i] = "A-XX ";
					} else {
						availabiltyMap.put("M", availabiltyMap.get("M") + 1);
						finalSeat[j][i] = "M-XX ";
					}
					totalAvailableSeat++;
				} else {
					finalSeat[j][i] = "";
				}
			}
		}
	}

	private static void allocateSeat(String category, String[][] seats, HashMap<String, Integer> availabiltyMap,
			Integer totalAvailableSeat) {

		for (int i = 0; i < seats[i].length; i++) {
			for (int j = 0; j < seats.length; j++) {
				if (seats[j][i].equals(category + "-XX") && currSeat <= totalAvailableSeat && noOfPassenger != 0) {
					seats[j][i] = category + "-" + currSeat + " ";
					currSeat++;
					availabiltyMap.put(category, availabiltyMap.get(category) - 1);
					noOfPassenger--;
				}
			}
		}
	}
}