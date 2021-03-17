package ece448.iot_sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jboss.logging.Message;
import org.junit.Test;

public class MqttCommandsTests {

	private static final MqttMessage MqttMessage = null;
	@Test
	public void test0() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		
		//String msg = "hello";

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		String topic = "iot_ece448/action/a/off";
		
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
		
		String topic = "iot_ece448/action/a/on";
		//String msg = "hello";

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		cmds.handleMessage(topic, MqttMessage);

		HTTPCommands cmd = new HTTPCommands(list);

		String result = cmd.handleGet("/a", new HashMap<>());


		assertTrue(result.indexOf("Plug a is on") !=-1);
	}

	@Test
	public void test2() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		String topic1 = "iot_ece448/action/a/on";
		String topic2 = "iot_ece448/action/a/toggle";
		//String msg = "hello";

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		cmds.handleMessage(topic1, MqttMessage);
		cmds.handleMessage(topic2, MqttMessage);

		HTTPCommands cmd = new HTTPCommands(list);

		String result = cmd.handleGet("/a", new HashMap<>());


		assertTrue(result.indexOf("Plug a is off") !=-1);
		
	
	}


	
@Test
	public void test3() {
		PlugSim a = new PlugSim(" ");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		String topic = "iot_ece448/action//on";
	

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		cmds.handleMessage(topic, MqttMessage);
		

		HTTPCommands cmd = new HTTPCommands(list);

		String result = cmd.handleGet("/", new HashMap<>());

		assertEquals(result,"<html><body><p><a href='/ '> </a></p></body></html>");
		//assertTrue(result.indexOf("Plug a is off") !=-1);
		
	}
	@Test
	public void test4() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		String topic = "iot_ece448/action/a/b/c/on";
	

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		cmds.handleMessage(topic, MqttMessage);
		

		HTTPCommands cmd = new HTTPCommands(list);

		String result = cmd.handleGet("/a", new HashMap<>());

		assertTrue(result.indexOf("Plug a is off") !=-1);
		
	}

	@Test
	public void test5() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		String topic = "iot_ece448/x/a/on";
	

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		cmds.handleMessage(topic, MqttMessage);
		

		HTTPCommands cmd = new HTTPCommands(list);

		String result = cmd.handleGet("/a", new HashMap<>());

		assertTrue(result.indexOf("Plug a is off") !=-1);
	}
    //test6 getTopic
	@Test
	public void test6() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		
	

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		String topic = cmds.getTopic();
		

		assertEquals(topic, "iot_ece448/action/#");
	}
     //MqttUpdate
	@Test
	public void test7() {

		MqttUpdates mqttUpd = new MqttUpdates("iot_ece448");
		String result = mqttUpd.getTopic("a", "state");
		
		
		assertEquals(result,"iot_ece448/update/a/state");
	}
	
	@Test
	public void test8()  {
		MqttUpdates mqttUpd = new MqttUpdates("iot_ece448");
		
		
		
		
		assertEquals(new String(mqttUpd.getMessage("on").getPayload()),"on");
		
	
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
