/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.util.List;

import com.mowitnow.lawnmower.exceptions.EngineException;

/**
 * The main class to launch application
 * 
 * @author Kiva
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.err.println("Usage: java -jar lawnmower.jar <filename>");
			System.exit(1);
		}
		launchApp(FileManager.parseFile(args[0]));
	}

	/**
	 * Launch the application with a {@link List} of String<br>
	 * Each entry must respect the file format
	 * 
	 * @param list
	 */
	public static void launchApp(List<String> list) {
		final IEngine engine = new EngineImpl();
		try {
			engine.createEngine(list);
		} catch (EngineException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		engine.run();
		engine.showResult();
	}
}
