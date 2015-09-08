package json.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Replace {

	public static void replace(String oldstring, String newstring, File in, File out) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(in));
		PrintWriter writer = new PrintWriter(new FileWriter(out));
		String line = null;
		while ((line = reader.readLine()) != null) {
			writer.println(line.replaceAll(oldstring,newstring));
		}
		reader.close();
		writer.close();
	}


	public static void main(String[] args) {
		try {
			String oldString = args[0];
			String newString = args[1];			
			File in = new File(args[2]);
			if (!in.exists()) {
				System.out.println("The input file " + in + " does not exist.");
				return;
			}
			File out = new File(args[3]);
			if (out.exists()) {
			      System.out.println("The output file " + out + " already exists.");
			      return;
			}
			replace(oldString, newString, in, out);
		} catch (Exception e) {
			
		}
	}
	

}
