/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.mowitnow.lawnmower.beans.Coordinate;
import com.mowitnow.lawnmower.beans.Garden;
import com.mowitnow.lawnmower.beans.Lawnmower;
import com.mowitnow.lawnmower.enums.MovementEnum;
import com.mowitnow.lawnmower.enums.OrientationEnum;
import com.mowitnow.lawnmower.exceptions.MowItNowException;

/**
 * 
 * @author Kiva
 * 
 */
public class EngineImpl implements IEngine {

	static String EMPTY_LIST = "Command list is empty";

	static String BAD_LIST = "Command list is malformed";

	static String OUT_GARDEN = "Some lawnmowers are out of the garden";

	private Garden garden;

	private List<String> listString;

	@Override
	public void createEngine(List<String> list) throws MowItNowException {
		if (CollectionUtils.isEmpty(list))
			throw new MowItNowException(EMPTY_LIST);
		garden = new Garden();
		// Create a new list because it'll be destroyed during the engine
		// creation
		listString = new ArrayList<String>(list);
		buildCoordinate();
		buildLawnmower();
		checkLawnmower();
	}

	/**
	 * This method checks that lawnmowers are well in the garden<br>
	 * The method assumes the garden is good and all lawnmowers too
	 */
	private void checkLawnmower() throws MowItNowException {
		for (Lawnmower lawnmower : garden.getLawnmowers()) {
			if (lawnmower.getCoordinate().getX() > garden.getCorner().getX()
					|| lawnmower.getCoordinate().getY() > garden.getCorner()
							.getY()) {
				throw new MowItNowException(OUT_GARDEN);
			}
		}
	}

	/**
	 * the lawnmower is build with two lines. The first line gives the position
	 * and the second, the movements list
	 * 
	 * @throws MowItNowException
	 */
	private void buildLawnmower() throws MowItNowException {
		if (CollectionUtils.isEmpty(listString))
			throw new MowItNowException(BAD_LIST);
		// Analyse the position
		final String firstLine = listString.remove(0);
		Pattern pattern = Pattern.compile("^([0-9]+) +([0-9]+) +([NWES])$");
		Matcher matcher = pattern.matcher(firstLine);
		if (!matcher.matches())
			throw new MowItNowException(BAD_LIST);
		final Coordinate coordinate = new Coordinate();
		coordinate.setX(Integer.parseInt(matcher.group(1)));
		coordinate.setY(Integer.parseInt(matcher.group(2)));
		final Lawnmower lawnmower = new Lawnmower();
		lawnmower.setCoordinate(coordinate);
		lawnmower
				.setCurrentOrientation(OrientationEnum.parse(matcher.group(3)));

		// Analyse the movements list. If no line, no movement
		if (listString.size() > 0) {
			final String secondeLine = listString.remove(0);
			if (StringUtils.isNotBlank(secondeLine)) {
				// We authorize more letters than A, D and G but only these one
				// are used
				if (!secondeLine.matches("^[A-Z ]*$"))
					throw new MowItNowException(BAD_LIST);
				final String[] splitLine = secondeLine.split("");
				if (ArrayUtils.isNotEmpty(splitLine)) {
					for (String movement : splitLine) {
						final MovementEnum movementEnum = MovementEnum
								.parse(movement);
						if (movementEnum != null)
							lawnmower.addMovement(movementEnum);
					}
				}
			}
		}
		garden.addLawnmower(lawnmower);
		// If the collection is not empty, it stays some lawnmower to build
		if (CollectionUtils.isNotEmpty(listString))
			buildLawnmower();
	}

	/**
	 * Get the first line and transform it into coordonate
	 * 
	 * @throws MowItNowException
	 */
	private void buildCoordinate() throws MowItNowException {
		// the first line is the garden coordinate, we remove it
		final String firstLine = listString.remove(0);
		if (StringUtils.isBlank(firstLine))
			throw new MowItNowException(BAD_LIST);
		// the first line format is "5 5". First 5 is x, second is y
		Pattern pattern = Pattern.compile("^([0-9]+) +([0-9]+)$");
		final Matcher matcher = pattern.matcher(firstLine);
		if (!matcher.matches())
			throw new MowItNowException(BAD_LIST);
		final Coordinate coordinate = new Coordinate();
		coordinate.setX(Integer.parseInt(matcher.group(1)));
		coordinate.setY(Integer.parseInt(matcher.group(2)));
		garden.setCorner(coordinate);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showResult() {
		if (garden != null
				&& CollectionUtils.isNotEmpty(garden.getLawnmowers())) {
			for (Lawnmower lawnmower : garden.getLawnmowers()) {
				if (lawnmower != null && lawnmower.getCoordinate() != null) {
					System.out.println(lawnmower.getCoordinate().getX() + " "
							+ lawnmower.getCoordinate().getY() + " "
							+ lawnmower.getCurrentOrientation());
				}
			}
		}

	}

	Garden getGarden() {
		return garden;
	}

}
