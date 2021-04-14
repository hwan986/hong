package ece448.iot_hub;



import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.env.Environment;
import org.apache.catalina.TomcatPrincipal;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;


import ece448.iot_sim.PlugSim;
import ece448.iot_sim.SimConfig;

//@Component
public class ServerBackendTests {

/*
	private static final String broker = "tcp://127.0.0.1";
	private static final String clientId = "iot_hub";
	private static final String topicPrefix = "iot_ece448";
	private final MqttController mqtt;
	private static final MqttMessage msg = new MqttMessage();
	


	//private static PlugsModel plugs;
	public ServerBackendTests() throws Exception{
		this.mqtt = new MqttController(broker, clientId, topicPrefix);
		
		this.mqtt.start();
		//this.plugs = plugs;
	}
	
	@Test
	public void test0() {
		
		mqtt.publishAction("a", "on");		
		byte[] ret = msg.getPayload();
		String s = new String(ret, StandardCharsets.UTF_8);
		
		assertEquals(s, "");
	
	}

	
	@Test
	public void test1() {
		String topic = topicPrefix +"/update/a/state";
		
		
		mqtt.handleUpdate(topic, new MqttMessage("on".getBytes()));

		assertEquals(mqtt.getState("a"), "on");

	}

	@Test
	public void test2() {
		String topic = topicPrefix +"/update/a/state";
		
		mqtt.handleUpdate(topic, new MqttMessage("on".getBytes()));
		TreeMap<String, String> ret = new TreeMap<>();
		ret.put("a","on");
		assertEquals(mqtt.getStates(), ret);
		
	}


	
@Test
	public void test3() {
		String topic = topicPrefix +"/update/a/power";
		
		mqtt.handleUpdate(topic, new MqttMessage("200".getBytes()));
		assertEquals(mqtt.getPower("a"), "200");
		
	}
	
	@Test
	public void test4() {
		String topic = topicPrefix +"/update/a/power";
		
		mqtt.handleUpdate(topic, new MqttMessage("200".getBytes()));
		TreeMap<String, String> ret = new TreeMap<>();
		ret.put("a","200");
		assertEquals(mqtt.getPowers(), ret);
		
	}
	
	@Test
	public void test5()  {
		String topic = topicPrefix +"/action/a/power";
		mqtt.handleUpdate(topic, new MqttMessage("200".getBytes()));
		TreeMap<String, String> ret = new TreeMap<>();
		assertEquals(mqtt.getPowers(), ret);
		
	
	}

	@Test
	public void test6() throws Exception {
		mqtt.close();
	}
	
	/*
    //test6 getTopic
	@Test
	public void test7() throws Exception {		
		//String broker = env.getProperty("mqtt.broker");
		//String clientID = env.getProperty("mqtt.clientId");
		//		
			
			
		
		
		PlugsModel plugs = new PlugsModel(broker,topicPrefix,clientId );
		PlugsResource ret = new PlugsResource(plugs);
		ret.createPlug("plug1", Arrays.asList("a","b","c"));
		mqtt.publishAction("a", "on");
		ret.getPlug("a", "on");
		ret.getPlugs();

		//ret.getPlug("a", "on");
		//plugs.getPlugs();
		//ret.getPlugs();
	}
	
	/*
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
