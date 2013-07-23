/**
 * 
 */
package com.mowitnow.lawnmower.enums;

/**
 * This enum is the orientation of the lawnmower<br>
 * It's build as a circle
 * 
 * @author Kiva
 * 
 */
public enum OrientationEnum {

	N, E, S, W;

	/**
	 * Parse the string to get the associated enum<br>
	 * If enum doesn't exist, return null
	 * 
	 * @param orientation
	 * @return
	 */
	public static OrientationEnum parse(String orientation) {
		for (OrientationEnum orientationEnum : values()) {
			if (orientationEnum.name().equals(orientation))
				return orientationEnum;
		}
		return null;
	}

}
