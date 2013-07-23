/**
 * 
 */
package com.mowitnow.lawnmower.enums;

/**
 * 
 * 
 * @author Kiva
 * 
 */
public enum MovementEnum {

	D, G, A;

	/**
	 * Parse the string to get the associated enum<br>
	 * If enum doesn't exist, return null
	 * 
	 * @param movement
	 * @return
	 */
	public static MovementEnum parse(String movement) {
		for (MovementEnum orientationEnum : values()) {
			if (orientationEnum.name().equals(movement))
				return orientationEnum;
		}
		return null;
	}

}
