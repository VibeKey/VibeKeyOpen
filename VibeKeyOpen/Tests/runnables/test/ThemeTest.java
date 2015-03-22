package runnables.test;

import static org.junit.Assert.*;

import org.junit.Test;

import theme.core.TestObj;
import theme.core.ThemeObject;

public class ThemeTest {

	@Test
	public void test() {
		ThemeObject obj = new TestObj();
		obj.makeObject("<TestObj id:4950rr></TestObj>", null);
		
		assertTrue("Did not get the correct id, got " + obj.getID(), obj.getID().equals("4950rr"));
	}

}
