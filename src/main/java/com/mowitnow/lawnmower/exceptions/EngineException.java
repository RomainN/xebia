/**
 * 
 */
package com.mowitnow.lawnmower.exceptions;

/**
 * @author Kiva
 * 
 */
public class EngineException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4721741120094265413L;

	public EngineException() {
		super();
	}

	public EngineException(String message) {
		super(message);
	}

	public EngineException(Throwable throwable) {
		super(throwable);
	}

	public EngineException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
