/**
 * 
 */
package com.mowitnow.lawnmower.exceptions;

/**
 * @author Kiva
 * 
 */
public class MowItNowException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4721741120094265413L;

	public MowItNowException() {
		super();
	}

	public MowItNowException(String message) {
		super(message);
	}

	public MowItNowException(Throwable throwable) {
		super(throwable);
	}

	public MowItNowException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
