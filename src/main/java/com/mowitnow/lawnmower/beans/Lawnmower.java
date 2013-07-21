/**
 * 
 */
package com.mowitnow.lawnmower.beans;

import java.util.ArrayList;
import java.util.List;

import com.mowitnow.lawnmower.enums.MovementEnum;
import com.mowitnow.lawnmower.enums.OrientationEnum;

/**
 * @author Kiva
 * 
 */
public class Lawnmower {

	/**
	 * Coordinates of the lawnmower on the map
	 */
	private Coordinate coordinate;

	private OrientationEnum currentOrientation;

	private List<MovementEnum> movements;

	public void addMovement(MovementEnum movementEnum) {
		if (movements == null)
			movements = new ArrayList<MovementEnum>();
		movements.add(movementEnum);
	}

	/**
	 * @return the coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @param coordinate
	 *            the coordinate to set
	 */
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * @return the currentOrientation
	 */
	public OrientationEnum getCurrentOrientation() {
		return currentOrientation;
	}

	/**
	 * @param currentOrientation
	 *            the currentOrientation to set
	 */
	public void setCurrentOrientation(OrientationEnum currentOrientation) {
		this.currentOrientation = currentOrientation;
	}

	/**
	 * @return the movements
	 */
	public List<MovementEnum> getMovements() {
		return movements;
	}

	/**
	 * @param movements
	 *            the movements to set
	 */
	public void setMovements(List<MovementEnum> movements) {
		this.movements = movements;
	}

}
