/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Kiva
 * 
 */
@RunWith(JUnit4.class)
public class MainTest {

	@Test
	public void main() {
		final String lineSeparator = System.getProperty("line.separator");
		final ByteArrayOutputStream err = new ByteArrayOutputStream();
		System.setErr(new PrintStream(err));
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		// Null test
		Main.main(null);
		Assert.assertEquals(Main.USAGE + lineSeparator, err.toString());
		Assert.assertEquals(0, out.size());

		err.reset();
		String[] args = { "1", "2" };
		Main.main(args);
		Assert.assertEquals(Main.USAGE + lineSeparator, err.toString());
		Assert.assertEquals(0, out.size());

		// File doesn't exist
		err.reset();
		String[] file = { "toto" };
		Main.main(file);
		Assert.assertNotEquals(0, err.size());
		Assert.assertEquals(0, out.size());

		// File ok
		err.reset();
		URL url = ClassLoader.getSystemResource("test1");
		String[] fileOk = { url.getPath() };
		Main.main(fileOk);
		Assert.assertEquals(0, err.size());
		Assert.assertEquals("1 3 N" + lineSeparator + "5 1 E" + lineSeparator,
				out.toString());

		// More complicated tests
		out.reset();
		url = ClassLoader.getSystemResource("test2");
		String[] test2 = { url.getPath() };
		Main.main(test2);
		Assert.assertEquals(0, err.size());
		Assert.assertEquals("0 10 E" + lineSeparator + "0 28 S" + lineSeparator
				+ "0 1 N" + lineSeparator, out.toString());

		out.reset();
		url = ClassLoader.getSystemResource("test3");
		String[] test3 = { url.getPath() };
		Main.main(test3);
		Assert.assertEquals(0, err.size());
		Assert.assertEquals("2 2 N" + lineSeparator, out.toString());

	}
}
