/**
 * 
 */
package com.mowitnow.lawnmower.beans;

/**
 * Reprent the point (x,y) on the map
 * 
 * @author Kiva
 * 
 */
public class Coordinate {

	private Integer x;

	private Integer y;

	public Coordinate() {
		super();
	}

	public Coordinate(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(Integer x) {
		this.x = x;
	}

}
