package ece448.iot_hub;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.TreeMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;


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
		
		PlugsModel plugs = new PlugsModel(broker,topicPrefix,clientId );
		PlugsResource ret = new PlugsResource(plugs);
		
		ret.createPlug("plug1", Arrays.asList("a","b","c"));
		mqtt.publishAction("a", "on");
		
		ret.getPlugs();
		mqtt.close();
	}
	
	
    
	@Test
	public void test7() throws Exception {									
		
		PlugsModel plugs = new PlugsModel(broker,topicPrefix,clientId );
		PlugsResource ret = new PlugsResource(plugs);
		
		ret.createPlug("plug1", Arrays.asList("a","b","c"));
		mqtt.publishAction("a", "on");
		
		ret.getPlug("a", "on");
		plugs.close();
		
	}
	
	@Test
	public void test8() throws Exception {

		PlugsModel plugs = new PlugsModel(broker,topicPrefix,clientId );
		PlugsResource ret = new PlugsResource(plugs);
		
		ret.createPlug("plug1", Arrays.asList("a","b","c"));
		mqtt.publishAction("a", "on");
		
		ret.getPlug("a", null);
		ret.getPlugs();
		ret.removePlug("a");
	}
	
	@Test
	public void test9() throws Exception  {
		
		PlugsModel plugs = new PlugsModel(broker,topicPrefix,clientId );
		PlugsResource ret = new PlugsResource(plugs);
		
		ret.createPlug("plug1", Arrays.asList("a","b","c"));
		mqtt.publishAction("a", "on");
		
		ret.getPlug("a.500", "");
		ret.getPlugs();
	
	}

}
