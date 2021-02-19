package ece448.iot_sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class HTTPCommandsTests {

	@Test
	public void testOutput() {
		PlugSim a = new PlugSim("a");
		PlugSim x = new PlugSim("x");

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		list.add(x);

		HTTPCommands cmds = new HTTPCommands(list);

		assertEquals(cmds.handleGet("/b", new HashMap<>()), null);
		//assertTrue(cmds.handleGet("/a", new HashMap<>()) != null);
	}




}
