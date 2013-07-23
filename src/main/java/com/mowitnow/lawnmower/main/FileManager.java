/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiva
 * 
 */
public class FileManager {

	final static private String ERROR_FILE_NOT_FOUND = "Your file does'nt exist or can't be read";

	final static private String ERROR_FILE = "An error has occured when the file read";

	/**
	 * Transform the file to a {@link List} of {@link String}
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<String> parseFile(String fileName) {
		List<String> list = new ArrayList<String>();
		final File file = new File(fileName);
		if (file.exists() && file.canRead()) {
			// Read the file line by line
			DataInputStream inputStream = null;
			try {
				FileInputStream fileStream = new FileInputStream(file);
				inputStream = new DataInputStream(fileStream);
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(inputStream));
				String strLine;
				while ((strLine = buffer.readLine()) != null) {
					list.add(strLine);
				}
				inputStream.close();
			} catch (FileNotFoundException e) {
				System.err.println(ERROR_FILE_NOT_FOUND);
			} catch (IOException e) {
				System.err.println(ERROR_FILE);
			}
		} else {
			System.err.println(ERROR_FILE_NOT_FOUND);
		}
		return list;
	}
}
