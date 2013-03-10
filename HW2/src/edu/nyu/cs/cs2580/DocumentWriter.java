package edu.nyu.cs.cs2580;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DocumentWriter {
	static String PATH = "/home/congyu/cs2580/hw1/g06/results/";
	static void logWriter(String fileName,String content) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				PATH + fileName, false)));
		out.println(content);
		out.close();
	}
}
