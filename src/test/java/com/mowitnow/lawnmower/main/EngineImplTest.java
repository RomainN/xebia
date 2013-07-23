/**
 * 
 */
package com.mowitnow.lawnmower.main;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.mowitnow.lawnmower.beans.Garden;
import com.mowitnow.lawnmower.beans.Lawnmower;
import com.mowitnow.lawnmower.enums.MovementEnum;
import com.mowitnow.lawnmower.enums.OrientationEnum;
import com.mowitnow.lawnmower.exceptions.EngineException;

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
		} catch (EngineException e) {
			Assert.assertEquals(EngineImpl.EMPTY_LIST, e.getMessage());
		}

		List<String> list = new ArrayList<String>();
		// Empty list
		try {
			engine.createEngine(list);
			Assert.fail();
		} catch (EngineException e) {
			Assert.assertEquals(EngineImpl.EMPTY_LIST, e.getMessage());
		}

		// Only with coordinate
		list.add("5 6");
		try {
			engine.createEngine(list);
			Assert.fail();
		} catch (EngineException e) {
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

}
