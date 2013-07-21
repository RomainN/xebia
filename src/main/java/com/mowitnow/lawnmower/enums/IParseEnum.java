/**
 * 
 */
package com.mowitnow.lawnmower.enums;

/**
 * @author Kiva
 * 
 */
public interface IParseEnum<T> {

	/**
	 * Parse the string to get the associated enum<br>
	 * If enum doesn't exist, return null
	 * 
	 * @param toParse
	 * @return
	 */
	public T parse(String toParse);

}
