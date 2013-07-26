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

import org.apache.commons.lang3.StringUtils;

import com.mowitnow.lawnmower.exceptions.MowItNowException;

/**
 * @author Kiva
 * 
 */
public class FileManager {

	final static String ERROR_FILE_NOT_FOUND = "Your file does'nt exist or can't be read";

	final static String ERROR_FILE = "An error has occured when the file read";

	/**
	 * Transform the file to a {@link List} of {@link String}
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<String> parseFile(String fileName)
			throws MowItNowException {
		if (StringUtils.isBlank(fileName))
			throw new MowItNowException(ERROR_FILE_NOT_FOUND);
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
				throw new MowItNowException(ERROR_FILE_NOT_FOUND);
			} catch (IOException e) {
				throw new MowItNowException(ERROR_FILE);
			}
		} else {
			throw new MowItNowException(ERROR_FILE_NOT_FOUND);
		}
		return list;
	}
}
