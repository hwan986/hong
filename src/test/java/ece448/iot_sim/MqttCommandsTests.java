package ece448.iot_sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

public class MqttCommandsTests {

	private static final MqttMessage MqttMessage = null;
	@Test
	public void test0() {
		PlugSim a = new PlugSim("a");
		PlugSim x = new PlugSim("x");

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		list.add(x);
		String topic = "iot_ece448/action/a/off";
		//String msg = "hello";

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		cmds.handleMessage(topic, MqttMessage);

		HTTPCommands cmd = new HTTPCommands(list);

		String result = cmd.handleGet("/a", new HashMap<>());


		assertTrue(result.indexOf("Plug a is off") !=-1);
		//assertEquals(cmds.handleGet("/b", new HashMap<>()), null);
		//assertTrue(cmds.handleGet("/a", new HashMap<>()) != null);
	}

	@Test
	public void test1() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		

		HTTPCommands cmds = new HTTPCommands(list);

		assertEquals(cmds.handleGet("/", new HashMap<>()), "<html><body><p><a href='/a'>a</a></p></body></html>");
		//assertTrue(cmds.handleGet("/a", new HashMap<>()) != null);
	}

	@Test
	public void test2() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		

		HTTPCommands cmds = new HTTPCommands(list);

		assertEquals(cmds.handleGet("//a", new HashMap<>()), null);
		//assertTrue(cmds.handleGet("/a", new HashMap<>()) != null);
	}


	
@Test
	public void test3() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", "on");

		String result  = cmds.handleGet("/a", params);
		assertTrue(result.indexOf("Plug a is on") !=-1 
		&& result.indexOf("Power reading is 0.000") !=-1) ;
		//assertEquals(result, "<html><body><p>Plug a is on.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
		
	}
	@Test
	public void test4() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", "off");

		String result  = cmds.handleGet("/a", params);
		assertTrue(result.indexOf("Plug a is off") !=-1 
		&& result.indexOf("Power reading is 0.000") !=-1) ;
		
		//assertEquals(result, "<html><body><p>Plug a is off.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
		
	}

	@Test
	public void test5() {
		PlugSim a = new PlugSim("a");
	

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
	

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", null);

		String result  = cmds.handleGet("/a", params);
		assertTrue(result.indexOf("Plug a is off") !=-1 ) ;
		//assertEquals(result, "<html><body><p>Plug a is off.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
	}

	@Test
	public void test6() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", "toggle");

		String result  = cmds.handleGet("/a", params);
		
		assertTrue(result.indexOf("Plug a is on") !=-1) ;
		//assertEquals(result, "<html><body><p>Plug a is on.</p><p>Power reading is 0.000.</p><p><a href='/a?action=on'>Switch On</a></p><p><a href='/a?action=off'>Switch Off</a></p><p><a href='/a?action=toggle'>Toggle</a></p></body></html>");
	}

	@Test
	public void test7() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		

		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		params.put("action", "to");

		String result  = cmds.handleGet("/a", params);
		
		assertEquals(result,"<html><body></body></html>");
	}
	
	@Test
	public void test8()  {
		PlugSim a = new PlugSim("a.700");
		
		
	
		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		
		params.put("action", "on");
		
	

		String result  = cmds.handleGet("/a.700", params);

		//a.measurePower();
		params.put("action","on");
		result  = cmds.handleGet("/a.700", params);
		//Thread.sleep(1500);
		
		//assertEquals(result,"");
		assertTrue(result.indexOf("Power reading is 0.000") !=-1);
		
	
	}
	@Test
	public void test9()  {
		PlugSim a = new PlugSim("a.700");
		
		
	
		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		HTTPCommands cmds = new HTTPCommands(list);

		HashMap<String,String> params = new HashMap<>();
		
		params.put("action", "on");
		
	

		String result  = cmds.handleGet("/a.700", params);

		a.measurePower();
		params.put("action","on");
		result  = cmds.handleGet("/a.700", params);
		//Thread.sleep(1500);
		
		//assertEquals(result,"");
		assertTrue(result.indexOf("Power reading is 700.000") !=-1);
		
	
	}


}
