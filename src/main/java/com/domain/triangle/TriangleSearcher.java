package com.domain.triangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriangleSearcher {
	private static final int ARR_CAPACITY = 6;
	private double maxSquare;
	private String coordinates;
	private int lineCounter = 1;
	private static Logger LOGGER = LoggerFactory.getLogger(TriangleSearcher.class);

	public void getLargestTriangleSquare(File file) {
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				double temp = selectLargestTriangleSquare(line);

				if (maxSquare < temp) {
					maxSquare = temp;
					coordinates = line;
				}
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
			LOGGER.error(e.getMessage());
		}
	}

	private double selectLargestTriangleSquare(String line) {
		String[] arrStr = line.split(" ");
		double square = 0;

		if (arrStr.length != ARR_CAPACITY) {
			LOGGER.info("Not enough coordinates at {} line", lineCounter);
			lineCounter++;
			return maxSquare;
		} else {
			int[] arr = getIntArr(arrStr);

			if (arr != null && isIsosceles(arr)) {
				square = getSquare(arr);
			}
		}
		return (square < 0) ? square * -1 : square;
	}

	private int[] getIntArr(String[] array) {
		
		int[] arr = new int[ARR_CAPACITY];
		try {
			for (int i = 0; i < array.length; i++) {
				arr[i] = Integer.parseInt(array[i]);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("Bad line {} {}" ,lineCounter , e.getMessage());
			lineCounter++;
			return null;
		}
		lineCounter++;
		return arr;
	}

	private double getSquare(int[] arr) {
		return 0.5 * ((arr[2] - arr[0]) * (arr[5] - arr[1]) -
					  (arr[4] - arr[0]) * (arr[3] - arr[1]));
	}

	public void writeCoordinateInFile(File file) {
		try (FileWriter writer = new FileWriter(file)) {
			
			if (coordinates != null) {
				writer.write(coordinates);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	private boolean isIsosceles(int[] arr) {
		double squareSumAB = (arr[2] - arr[0]) * (arr[2] - arr[0]) +
							 (arr[3] - arr[1]) * (arr[3] - arr[1]);
		double squareSumAC = (arr[4] - arr[0]) * (arr[4] - arr[0]) +
							 (arr[5] - arr[1]) * (arr[5] - arr[1]);
		double squareSumBC = (arr[4] - arr[2]) * (arr[4] - arr[2]) +
							 (arr[5] - arr[3]) * (arr[5] - arr[3]);
		return (squareSumAB == squareSumAC ||
				squareSumAB == squareSumBC ||
				squareSumBC == squareSumAC);
	}
}
