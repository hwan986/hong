package ece448.iot_sim;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlugSimTests {

	@Test
	public void testInit() {
		PlugSim plug = new PlugSim("a");

		assertFalse(plug.isOn());
	}

	@Test
	public void testSwitchOn() {
		PlugSim plug = new PlugSim("a");

		plug.switchOn();

		assertTrue(plug.isOn());
	}
}
