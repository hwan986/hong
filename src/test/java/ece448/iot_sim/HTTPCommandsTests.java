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

	@Test
	public void test2() {
		PlugSim a = new PlugSim("a");
		PlugSim x = new PlugSim("x");

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		list.add(x);

		HTTPCommands cmds = new HTTPCommands(list);

		assertEquals(cmds.handleGet("/", new HashMap<>()), "<html><body><p><a href='/a'>a</a></p><p><a href='/x'>x</a></p></body></html>");
		//assertTrue(cmds.handleGet("/a", new HashMap<>()) != null);
	}


	
@Test
	public void test3() {
		PlugSim a = new PlugSim("a");
		PlugSim x = new PlugSim("x");

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		list.add(x);

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", "on");

		String result  = cmds.handleGet("/a", params);
		//assertEquals(true, a.isOn());
		assertEquals(result, "<html><body><p>Plug a is on.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
		
	}
	@Test
	public void test4() {
		PlugSim a = new PlugSim("a");
		PlugSim x = new PlugSim("x");

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		list.add(x);

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", "off");

		String result  = cmds.handleGet("/a", params);
		//assertEquals(true, a.isOn());
		assertEquals(result, "<html><body><p>Plug a is off.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
		
	}

	@Test
	public void test5() {
		PlugSim a = new PlugSim("a");
		PlugSim x = new PlugSim("x");

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		list.add(x);

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", null);

		String result  = cmds.handleGet("/a", params);
		//assertEquals(true, a.isOn());
		assertEquals(result, "<html><body><p>Plug a is off.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
	}

	@Test
	public void test6() {
		PlugSim a = new PlugSim("a");
		PlugSim x = new PlugSim("x");

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		list.add(x);

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", "toggle");

		String result  = cmds.handleGet("/a", params);
		//assertEquals(true, a.isOn());
		assertEquals(result, "<html><body><p>Plug a is on.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
	}



}
