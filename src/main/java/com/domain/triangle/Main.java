package com.domain.triangle;

import java.io.File;
import org.apache.log4j.BasicConfigurator;

public class Main {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		TriangleSearcher triangleSearcher = new TriangleSearcher();
		
		triangleSearcher.getLargestTriangleSquare(new File(args[0]));
		triangleSearcher.writeCoordinateInFile(new File(args[1]));
	}
}
