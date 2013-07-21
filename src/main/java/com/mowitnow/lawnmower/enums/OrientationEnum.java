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
public enum OrientationEnum implements IParseEnum<OrientationEnum> {

	N, E, W, S;

	/**
	 * @param orientation
	 * @return
	 */
	@Override
	public OrientationEnum parse(String orientation) {
		for (OrientationEnum orientationEnum : values()) {
			if (orientationEnum.name().equals(orientation))
				return orientationEnum;
		}
		return null;
	}

}
