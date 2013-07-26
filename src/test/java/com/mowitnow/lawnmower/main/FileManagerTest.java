/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.mowitnow.lawnmower.exceptions.MowItNowException;

/**
 * @author Kiva
 * 
 */
@RunWith(JUnit4.class)
public class FileManagerTest {

	@Test
	public void parseFile() throws MowItNowException {
		// Null file
		try {
			FileManager.parseFile(null);
			Assert.fail();
		} catch (MowItNowException e) {
			Assert.assertEquals(FileManager.ERROR_FILE_NOT_FOUND,
					e.getMessage());
		}

		// File doesn't exist
		try {
			FileManager.parseFile("toto");
			Assert.fail();
		} catch (MowItNowException e) {
			Assert.assertEquals(FileManager.ERROR_FILE_NOT_FOUND,
					e.getMessage());
		}

		// File exist
		URL url = ClassLoader.getSystemResource("test1");
		List<String> listString = FileManager.parseFile(url.getPath());
		Assert.assertEquals(5, listString.size());
		Assert.assertEquals("5    5", listString.get(0));
		Assert.assertEquals("1 2 N", listString.get(1));
		Assert.assertEquals("GAGAGAGAA", listString.get(2));
		Assert.assertEquals("3 3 E", listString.get(3));
		Assert.assertEquals("AADAADADDA", listString.get(4));

		// Empty file
		url = ClassLoader.getSystemResource("emptyFile");
		listString = FileManager.parseFile(url.getPath());
		Assert.assertEquals(0, listString.size());

	}
}
