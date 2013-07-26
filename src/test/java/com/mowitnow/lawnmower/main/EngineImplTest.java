/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.mowitnow.lawnmower.beans.Coordinate;
import com.mowitnow.lawnmower.beans.Garden;
import com.mowitnow.lawnmower.beans.Lawnmower;
import com.mowitnow.lawnmower.enums.MovementEnum;
import com.mowitnow.lawnmower.enums.OrientationEnum;
import com.mowitnow.lawnmower.exceptions.MowItNowException;

/**
 * @author Kiva
 * 
 */
@RunWith(JUnit4.class)
public class EngineImplTest {

	IEngine engine = new EngineImpl();

	/**
	 * No test private method deeply
	 */
	@Test
	public void createEngine() throws Exception {
		// Null test
		try {
			engine.createEngine(null);
			Assert.fail();
		} catch (MowItNowException e) {
			Assert.assertEquals(EngineImpl.EMPTY_LIST, e.getMessage());
		}

		List<String> list = new ArrayList<String>();
		// Empty list
		try {
			engine.createEngine(list);
			Assert.fail();
		} catch (MowItNowException e) {
			Assert.assertEquals(EngineImpl.EMPTY_LIST, e.getMessage());
		}

		// Only with coordinate
		list.add("5 6");
		try {
			engine.createEngine(list);
			Assert.fail();
		} catch (MowItNowException e) {
			Assert.assertEquals(EngineImpl.BAD_LIST, e.getMessage());
		}

		// With a lawnmower
		list.add("1 2 N");
		list.add("AAD");
		engine.createEngine(list);
		Garden garden = ((EngineImpl) engine).getGarden();
		Assert.assertEquals((Integer) 5, garden.getCorner().getX());
		Assert.assertEquals((Integer) 6, garden.getCorner().getY());
		Assert.assertEquals(1, garden.getLawnmowers().size());
		Assert.assertEquals((Integer) 1, garden.getLawnmowers().get(0)
				.getCoordinate().getX());
		Assert.assertEquals((Integer) 2, garden.getLawnmowers().get(0)
				.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.N, garden.getLawnmowers().get(0)
				.getCurrentOrientation());
		Assert.assertEquals(3, garden.getLawnmowers().get(0).getMovements()
				.size());
		Assert.assertEquals(MovementEnum.A, garden.getLawnmowers().get(0)
				.getMovements().get(0));
		Assert.assertEquals(MovementEnum.A, garden.getLawnmowers().get(0)
				.getMovements().get(1));
		Assert.assertEquals(MovementEnum.D, garden.getLawnmowers().get(0)
				.getMovements().get(2));

		// With a second lawnmower
		list.add("3 2 S");
		list.add("A");
		engine.createEngine(list);
		garden = ((EngineImpl) engine).getGarden();
		Assert.assertEquals((Integer) 5, garden.getCorner().getX());
		Assert.assertEquals((Integer) 6, garden.getCorner().getY());
		Assert.assertEquals(2, garden.getLawnmowers().size());
		Assert.assertEquals((Integer) 1, garden.getLawnmowers().get(0)
				.getCoordinate().getX());
		Assert.assertEquals((Integer) 2, garden.getLawnmowers().get(0)
				.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.N, garden.getLawnmowers().get(0)
				.getCurrentOrientation());
		Assert.assertEquals(3, garden.getLawnmowers().get(0).getMovements()
				.size());
		Assert.assertEquals(MovementEnum.A, garden.getLawnmowers().get(0)
				.getMovements().get(0));
		Assert.assertEquals(MovementEnum.A, garden.getLawnmowers().get(0)
				.getMovements().get(1));
		Assert.assertEquals(MovementEnum.D, garden.getLawnmowers().get(0)
				.getMovements().get(2));
		Assert.assertEquals((Integer) 3, garden.getLawnmowers().get(1)
				.getCoordinate().getX());
		Assert.assertEquals((Integer) 2, garden.getLawnmowers().get(1)
				.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.S, garden.getLawnmowers().get(1)
				.getCurrentOrientation());
		Assert.assertEquals(1, garden.getLawnmowers().get(1).getMovements()
				.size());
		Assert.assertEquals(MovementEnum.A, garden.getLawnmowers().get(0)
				.getMovements().get(1));

		// One lawnmower is out of the garden
		list.add("3 22 S");
		list.add("A");
		try {
			engine.createEngine(list);
			Assert.fail();
		} catch (MowItNowException e) {
			Assert.assertEquals(EngineImpl.OUT_GARDEN, e.getMessage());
		}

	}

	@Test
	public void buildCoordinate() throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			NoSuchFieldException, SecurityException {
		final Method method = EngineImpl.class
				.getDeclaredMethod("buildCoordinate");
		method.setAccessible(true);
		final Field fieldList = EngineImpl.class.getDeclaredField("listString");
		fieldList.setAccessible(true);
		final Field fieldGarden = EngineImpl.class.getDeclaredField("garden");
		fieldGarden.setAccessible(true);

		// Test first line blank
		List<String> listString = new ArrayList<String>();
		listString.add("");
		final Garden garden = new Garden();
		fieldList.set(engine, listString);
		fieldGarden.set(engine, garden);
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(EngineImpl.BAD_LIST, e.getCause().getMessage());
		}

		// Malformed line
		listString.clear();
		listString.add("test");
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(EngineImpl.BAD_LIST, e.getCause().getMessage());
		}

		// Good coordinate
		listString.clear();
		listString.add("1 23");
		method.invoke(engine);
		Assert.assertEquals((Integer) 1, garden.getCorner().getX());
		Assert.assertEquals((Integer) 23, garden.getCorner().getY());

		// Lets spaces
		listString.clear();
		listString.add("12      23");
		method.invoke(engine);
		Assert.assertEquals((Integer) 12, garden.getCorner().getX());
		Assert.assertEquals((Integer) 23, garden.getCorner().getY());

		// Don't take negative numbers
		listString.clear();
		listString.add("12      -23");
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(EngineImpl.BAD_LIST, e.getCause().getMessage());
		}

	}

	@Test
	public void buildLawnmower() throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			NoSuchFieldException, SecurityException {
		final Method method = EngineImpl.class
				.getDeclaredMethod("buildLawnmower");
		method.setAccessible(true);
		final Field fieldList = EngineImpl.class.getDeclaredField("listString");
		fieldList.setAccessible(true);
		final Field fieldGarden = EngineImpl.class.getDeclaredField("garden");
		fieldGarden.setAccessible(true);

		// Test empty list
		List<String> listString = new ArrayList<String>();
		final Garden garden = new Garden();
		fieldList.set(engine, listString);
		fieldGarden.set(engine, garden);
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(EngineImpl.BAD_LIST, e.getCause().getMessage());
		}

		// Malformed first line
		listString.clear();
		listString.add("test");
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(EngineImpl.BAD_LIST, e.getCause().getMessage());
		}

		// Good coordinate, no movement
		listString.clear();
		listString.add("1 23 N");
		method.invoke(engine);
		Lawnmower lawnmower = garden.getLawnmowers().get(0);
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 23, lawnmower.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.N,
				lawnmower.getCurrentOrientation());
		Assert.assertNull(lawnmower.getMovements());

		// Second line malformed
		garden.getLawnmowers().clear();
		listString.clear();
		listString.add("1 23 N");
		listString.add("1 23 N");
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(EngineImpl.BAD_LIST, e.getCause().getMessage());
		}

		// Good lawnmower with movements
		listString.clear();
		listString.add("1 23 W");
		listString.add("ADG");
		method.invoke(engine);
		lawnmower = garden.getLawnmowers().get(0);
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 23, lawnmower.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.W,
				lawnmower.getCurrentOrientation());
		Assert.assertEquals(3, lawnmower.getMovements().size());
		Assert.assertEquals(MovementEnum.A, lawnmower.getMovements().get(0));
		Assert.assertEquals(MovementEnum.D, lawnmower.getMovements().get(1));
		Assert.assertEquals(MovementEnum.G, lawnmower.getMovements().get(2));

		// Two more lawnmower, one with strange movements and one with blank
		// line movement
		garden.getLawnmowers().clear();
		listString.clear();
		listString.add("1 23 W");
		listString.add("ADG");
		listString.add("2 2 S");
		listString.add("ADGJZHHA");
		listString.add("1 1 E");
		listString.add("");
		method.invoke(engine);
		lawnmower = garden.getLawnmowers().get(0);
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 23, lawnmower.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.W,
				lawnmower.getCurrentOrientation());
		Assert.assertEquals(3, lawnmower.getMovements().size());
		Assert.assertEquals(MovementEnum.A, lawnmower.getMovements().get(0));
		Assert.assertEquals(MovementEnum.D, lawnmower.getMovements().get(1));
		Assert.assertEquals(MovementEnum.G, lawnmower.getMovements().get(2));

		lawnmower = garden.getLawnmowers().get(1);
		Assert.assertEquals((Integer) 2, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 2, lawnmower.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.S,
				lawnmower.getCurrentOrientation());
		Assert.assertEquals(4, lawnmower.getMovements().size());
		Assert.assertEquals(MovementEnum.A, lawnmower.getMovements().get(0));
		Assert.assertEquals(MovementEnum.D, lawnmower.getMovements().get(1));
		Assert.assertEquals(MovementEnum.G, lawnmower.getMovements().get(2));
		Assert.assertEquals(MovementEnum.A, lawnmower.getMovements().get(3));

		lawnmower = garden.getLawnmowers().get(2);
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.E,
				lawnmower.getCurrentOrientation());
		Assert.assertNull(lawnmower.getMovements());

	}

	@Test
	public void showResult() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		final Field fieldGarden = EngineImpl.class.getDeclaredField("garden");
		fieldGarden.setAccessible(true);

		final Garden garden = new Garden();

		// Null test
		engine.showResult();

		// Empty list test
		fieldGarden.set(engine, garden);
		engine.showResult();

		// Lawnmower null
		garden.addLawnmower(null);
		engine.showResult();

		// Empty lawnmower
		Lawnmower lawnmower = new Lawnmower();
		garden.addLawnmower(lawnmower);

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		engine.showResult();
		Assert.assertEquals("", out.toString());

		// One good lawnmower
		lawnmower.setCoordinate(new Coordinate(1, 10));
		lawnmower.setCurrentOrientation(OrientationEnum.E);
		out.reset();
		engine.showResult();
		Assert.assertEquals("1 10 E", out.toString().trim());
	}

	@Test
	public void checkLawnmower() throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			NoSuchFieldException, SecurityException {
		final Method method = EngineImpl.class
				.getDeclaredMethod("checkLawnmower");
		method.setAccessible(true);
		final Field fieldGarden = EngineImpl.class.getDeclaredField("garden");
		fieldGarden.setAccessible(true);
		final Garden garden = new Garden();
		garden.setCorner(new Coordinate(3, 4));
		fieldGarden.set(engine, garden);

		// Good lawnmower
		Lawnmower lawnmower = new Lawnmower();
		lawnmower.setCoordinate(new Coordinate(1, 1));
		garden.addLawnmower(lawnmower);
		method.invoke(engine);

		// Lawnmower out by x
		lawnmower.getCoordinate().setX(10);
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (InvocationTargetException e) {
			Assert.assertEquals(EngineImpl.OUT_GARDEN, e.getTargetException()
					.getMessage());
		}

		// Lawnmower out by y
		lawnmower.getCoordinate().setX(0);
		lawnmower.getCoordinate().setY(5);
		try {
			method.invoke(engine);
			Assert.fail();
		} catch (InvocationTargetException e) {
			Assert.assertEquals(EngineImpl.OUT_GARDEN, e.getTargetException()
					.getMessage());
		}
	}

	@Test
	public void turnLawnmower() throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			NoSuchFieldException, SecurityException {
		final Method method = EngineImpl.class.getDeclaredMethod(
				"turnLawnmower", Lawnmower.class, MovementEnum.class);
		method.setAccessible(true);

		Lawnmower lawnmower = new Lawnmower();
		lawnmower.setCurrentOrientation(OrientationEnum.N);
		// We make the turn to go at N
		method.invoke(engine, lawnmower, MovementEnum.D);
		Assert.assertEquals(OrientationEnum.E,
				lawnmower.getCurrentOrientation());
		method.invoke(engine, lawnmower, MovementEnum.D);
		Assert.assertEquals(OrientationEnum.S,
				lawnmower.getCurrentOrientation());
		method.invoke(engine, lawnmower, MovementEnum.D);
		Assert.assertEquals(OrientationEnum.W,
				lawnmower.getCurrentOrientation());
		method.invoke(engine, lawnmower, MovementEnum.D);
		Assert.assertEquals(OrientationEnum.N,
				lawnmower.getCurrentOrientation());

		// The same in the other direction
		method.invoke(engine, lawnmower, MovementEnum.G);
		Assert.assertEquals(OrientationEnum.W,
				lawnmower.getCurrentOrientation());
		method.invoke(engine, lawnmower, MovementEnum.G);
		Assert.assertEquals(OrientationEnum.S,
				lawnmower.getCurrentOrientation());
		method.invoke(engine, lawnmower, MovementEnum.G);
		Assert.assertEquals(OrientationEnum.E,
				lawnmower.getCurrentOrientation());
		method.invoke(engine, lawnmower, MovementEnum.G);
		Assert.assertEquals(OrientationEnum.N,
				lawnmower.getCurrentOrientation());
	}

	@Test
	public void goLawnmower() throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			NoSuchFieldException, SecurityException {
		final Method method = EngineImpl.class.getDeclaredMethod("goLawnmower",
				Lawnmower.class);
		method.setAccessible(true);
		final Field fieldGarden = EngineImpl.class.getDeclaredField("garden");
		fieldGarden.setAccessible(true);
		final Garden garden = new Garden();
		garden.setCorner(new Coordinate(3, 4));
		fieldGarden.set(engine, garden);

		Lawnmower lawnmower = new Lawnmower();
		lawnmower.setCurrentOrientation(OrientationEnum.N);
		lawnmower.setCoordinate(new Coordinate(0, 0));
		// The garden is 3,4. The lawnmower does the turn
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 2, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 4, lawnmower.getCoordinate().getY());

		// On the top, can't go ahead
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 4, lawnmower.getCoordinate().getY());

		// To right
		lawnmower.setCurrentOrientation(OrientationEnum.E);
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 4, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 2, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 4, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 4, lawnmower.getCoordinate().getY());

		// On the top right, can't go ahead
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 4, lawnmower.getCoordinate().getY());

		// To right
		lawnmower.setCurrentOrientation(OrientationEnum.S);
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 2, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getY());

		// On bottom right, can't go ahead
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 3, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getY());

		// To right
		lawnmower.setCurrentOrientation(OrientationEnum.W);
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 2, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 1, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getY());

		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getY());

		// The turn is finished, can't go ahead
		method.invoke(engine, lawnmower);
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getX());
		Assert.assertEquals((Integer) 0, lawnmower.getCoordinate().getY());
	}

	@Test
	public void run() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		final Field fieldGarden = EngineImpl.class.getDeclaredField("garden");
		fieldGarden.setAccessible(true);

		final Garden garden = new Garden();
		garden.setCorner(new Coordinate(3, 2));
		// Null test
		engine.run();

		// Empty list test
		fieldGarden.set(engine, garden);
		engine.run();

		// One lawnmowers with movements, one without
		Lawnmower lawnmower1 = new Lawnmower();
		lawnmower1.setCoordinate(new Coordinate(0, 0));
		lawnmower1.setCurrentOrientation(OrientationEnum.W);
		lawnmower1.addMovement(MovementEnum.A);
		lawnmower1.addMovement(MovementEnum.G);
		lawnmower1.addMovement(MovementEnum.G);
		lawnmower1.addMovement(MovementEnum.A);
		lawnmower1.addMovement(MovementEnum.A);
		lawnmower1.addMovement(MovementEnum.A);
		lawnmower1.addMovement(MovementEnum.A);
		lawnmower1.addMovement(MovementEnum.A);
		lawnmower1.addMovement(MovementEnum.A);
		lawnmower1.addMovement(MovementEnum.D);
		garden.addLawnmower(lawnmower1);

		Lawnmower lawnmower2 = new Lawnmower();
		lawnmower2.setCoordinate(new Coordinate(1, 2));
		lawnmower2.setCurrentOrientation(OrientationEnum.S);
		garden.addLawnmower(lawnmower2);

		engine.run();
		Assert.assertEquals(OrientationEnum.S,
				lawnmower1.getCurrentOrientation());
		Assert.assertEquals((Integer) 3, lawnmower1.getCoordinate().getX());
		Assert.assertEquals((Integer) 0, lawnmower1.getCoordinate().getY());
		Assert.assertEquals(OrientationEnum.S,
				lawnmower2.getCurrentOrientation());
		Assert.assertEquals((Integer) 1, lawnmower2.getCoordinate().getX());
		Assert.assertEquals((Integer) 2, lawnmower2.getCoordinate().getY());
	}
}
