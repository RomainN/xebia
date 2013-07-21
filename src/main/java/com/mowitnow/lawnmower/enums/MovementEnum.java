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
public enum MovementEnum implements IParseEnum<MovementEnum> {

	D, G, A;

	/**
	 * @param movement
	 * @return
	 */
	@Override
	public MovementEnum parse(String movement) {
		for (MovementEnum orientationEnum : values()) {
			if (orientationEnum.name().equals(movement))
				return orientationEnum;
		}
		return null;
	}

}
