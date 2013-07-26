/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.util.List;

import com.mowitnow.lawnmower.exceptions.MowItNowException;

/**
 * 
 * This class defines methods needed to execute the program
 * 
 * @author Kiva
 * 
 */
public interface IEngine {

	/**
	 * Create all elements for the engine from the list
	 * 
	 * @param list
	 * @throws MowItNowException
	 */
	public void createEngine(List<String> list) throws MowItNowException;

	/**
	 * Run the engine
	 */
	public void run();

	/**
	 * Show the result. That's mean the last position of lawnmowers
	 */
	public void showResult();

}
