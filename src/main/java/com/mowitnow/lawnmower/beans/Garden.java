/**
 * 
 */
package com.mowitnow.lawnmower.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The garden class is the rectangle where lawnmowers run
 * 
 * @author Kiva
 * 
 */
public class Garden {

	/**
	 * The corner is the top right point of the garden
	 */
	private Coordinate corner;

	private List<Lawnmower> lawnmowers;

	public void addLawnmower(Lawnmower lawnmower) {
		if (lawnmower == null)
			lawnmowers = new ArrayList<Lawnmower>();
		lawnmowers.add(lawnmower);
	}

	/**
	 * @return the corner
	 */
	public Coordinate getCorner() {
		return corner;
	}

	/**
	 * @param corner
	 *            the corner to set
	 */
	public void setCorner(Coordinate corner) {
		this.corner = corner;
	}

	/**
	 * @return the lawnmowers
	 */
	public List<Lawnmower> getLawnmowers() {
		return lawnmowers;
	}

	/**
	 * @param lawnmowers
	 *            the lawnmowers to set
	 */
	public void setLawnmowers(List<Lawnmower> lawnmowers) {
		this.lawnmowers = lawnmowers;
	}

}
