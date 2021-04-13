package ece448.iot_hub;



import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

import ece448.iot_sim.PlugSim;

public class ServerBackendTests {

	private static final String broker = "tcp://127.0.0.1";
	private static final String clientId = "iot_hub";
	private static final String topicPrefix = "iot_ece448";
	private final MqttController mqtt;
	private static final MqttMessage msg = new MqttMessage();

	
	public ServerBackendTests() throws Exception{
		this.mqtt = new MqttController(broker, clientId, topicPrefix);
		this.mqtt.start();
	}
 
	@Test
	public void test0() {
		//PlugsModel plugs;
		//PlugsResource ret = new PlugsResource(plugs);
		mqtt.publishAction("a", "on");		
		byte[] ret = msg.getPayload();
		String s = new String(ret, StandardCharsets.UTF_8);
		
		assertEquals(s, "");
	
	}

	
	@Test
	public void test1() {
		String topic = topicPrefix +"/update/a/state";
		mqtt.publishAction("a", "on");
		
		mqtt.handleUpdate(topic, new MqttMessage("on".getBytes()));

		assertEquals(mqtt.getState("a"), "on");

	}

	@Test
	public void test2() {
		String topic = topicPrefix +"/update/a/state";
		mqtt.publishAction("a", "on");
		mqtt.handleUpdate(topic, new MqttMessage("on".getBytes()));
		TreeMap<String, String> ret = new TreeMap<>();
		ret.put("a","on");
		assertEquals(mqtt.getStates(), ret);
		
	}


	
@Test
	public void test3() {
		String topic = topicPrefix +"/update/a/power";
		mqtt.publishAction("a", "on");
		mqtt.handleUpdate(topic, new MqttMessage("200".getBytes()));
		assertEquals(mqtt.getPower("a"), "200");
		
	}
	
	@Test
	public void test4() {
		String topic = topicPrefix +"/update/a/power";
		mqtt.publishAction("a", "on");
		mqtt.handleUpdate(topic, new MqttMessage("200".getBytes()));
		TreeMap<String, String> ret = new TreeMap<>();
		ret.put("a","200");
		assertEquals(mqtt.getPowers(), ret);
		
	}
	/*
	@Test
	public void test5()  {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		String topic = "iot_ece448/action/b/on";
		//String msg = "hello";

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		cmds.handleMessage(topic, MqttMessage);

		HTTPCommands cmd = new HTTPCommands(list);

		String result = cmd.handleGet("/a", new HashMap<>());


		assertTrue(result.indexOf("Plug a is off") !=-1);
		
	
	}
/*
	@Test
	public void test6() {
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
	public void test7() {
		PlugSim a = new PlugSim("a");
		

		ArrayList<PlugSim> list = new ArrayList<>();
		list.add(a);
		
		
	

		MqttCommands cmds = new MqttCommands(list, "iot_ece448");
		String topic = cmds.getTopic();
		

		assertEquals(topic, "iot_ece448/action/#");
	}
     //MqttUpdate
	@Test
	public void test8() {

		MqttUpdates mqttUpd = new MqttUpdates("iot_ece448");
		String result = mqttUpd.getTopic("a", "state");
		
		
		assertEquals(result,"iot_ece448/update/a/state");
	}
	
	@Test
	public void test9()  {
		MqttUpdates mqttUpd = new MqttUpdates("iot_ece448");
		
		
		
		
		assertEquals(new String(mqttUpd.getMessage("on").getPayload()),"on");
		
	
	}

*/

}
