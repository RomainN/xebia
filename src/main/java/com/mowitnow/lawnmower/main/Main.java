/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.util.List;

import com.mowitnow.lawnmower.exceptions.MowItNowException;

/**
 * The main class to launch application
 * 
 * @author Kiva
 * 
 */
public class Main {

	final static String USAGE = "Usage: java -jar lawnmower.jar <filename>";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.err.println(USAGE);
			return;
		}
		try {
			launchApp(FileManager.parseFile(args[0]));
		} catch (MowItNowException e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	/**
	 * Launch the application with a {@link List} of String<br>
	 * Each entry must respect the file format
	 * 
	 * @param list
	 */
	public static void launchApp(List<String> list) throws MowItNowException {
		final IEngine engine = new EngineImpl();
		engine.createEngine(list);
		engine.run();
		engine.showResult();
	}
}
