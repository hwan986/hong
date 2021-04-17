package ece448.iot_hub;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

import ece448.iot_sim.PlugSim;


public class GroupManagementTests {


	private static final String broker = "tcp://127.0.0.1";
	private static final String clientId = "iot_hub";
	private static final String topicPrefix = "iot_ece448";
	private final MqttController mqtt;
	private static final MqttMessage msg = new MqttMessage();
	
	
	public GroupManagementTests() throws Exception{
		
		this.mqtt = new MqttController(broker, clientId, topicPrefix);
		this.mqtt.start();
		
	}
	
	@Test
	public void test0() throws Exception {
		
		PlugSim plug1 = new PlugSim("a");
		PlugSim plug2 = new PlugSim("b");
		PlugsModel plug = new PlugsModel(broker, clientId,topicPrefix);

		GroupsModel group = new GroupsModel(broker, clientId,topicPrefix);
		GroupsResource groups = new GroupsResource(group, plug);

		ArrayList<PlugSim> plugs = new ArrayList<>();
		plugs.add(plug1);
		plugs.add(plug2);

		ArrayList<String> members = new ArrayList<>();
		members.add("a");
		members.add("b");

		groups.createGroup("x", members);
	
		assertNotNull(groups.getGroup("x",null));
		

	
	
	}

	
	@Test
	public void test1() throws Exception {
		
		PlugSim plug1 = new PlugSim("a");
		PlugSim plug2 = new PlugSim("b");
		PlugsModel plug = new PlugsModel(broker, clientId,topicPrefix);

		GroupsModel group = new GroupsModel(broker, clientId,topicPrefix);
		GroupsResource groups = new GroupsResource(group, plug);

		ArrayList<PlugSim> plugs = new ArrayList<>();
		plugs.add(plug1);
		plugs.add(plug2);

		ArrayList<String> members = new ArrayList<>();
		members.add("a");
		members.add("b");

		groups.createGroup("x", members);
		groups.removeGroup("x");
	
		assertNotNull(groups.getGroup("x",null));

	}

	@Test
	public void test2() throws Exception {
		
		PlugSim plug1 = new PlugSim("a");
		PlugSim plug2 = new PlugSim("b");
		PlugsModel plug = new PlugsModel(broker, clientId,topicPrefix);

		GroupsModel group = new GroupsModel(broker, clientId,topicPrefix);
		GroupsResource groups = new GroupsResource(group, plug);

		ArrayList<PlugSim> plugs = new ArrayList<>();
		plugs.add(plug1);
		plugs.add(plug2);

		ArrayList<String> members = new ArrayList<>();
		members.add("a");
		members.add("b");

		groups.createGroup("x", members);
		
	
		assertNotNull(groups.getGroup("x","on"));
		
	}


	
@Test
	public void test3() throws Exception {
		
		
		PlugSim plug1 = new PlugSim("a");
		PlugSim plug2 = new PlugSim("b");
		PlugsModel plug = new PlugsModel(broker, clientId,topicPrefix);

		GroupsModel group = new GroupsModel(broker, clientId,topicPrefix);
		GroupsResource groups = new GroupsResource(group, plug);

		ArrayList<PlugSim> plugs = new ArrayList<>();
		plugs.add(plug1);
		plugs.add(plug2);

		ArrayList<String> members = new ArrayList<>();
		members.add("a");
		members.add("b");

		groups.createGroup("x", members);
		
	
		assertNotNull(groups.getGroups());
		
	}
	
	@Test
	public void test4() throws Exception {
		

		PlugSim plug1 = new PlugSim("a");
		PlugSim plug2 = new PlugSim("b.500");
		PlugsModel plug = new PlugsModel(broker, clientId,topicPrefix);

		GroupsModel group = new GroupsModel(broker, clientId,topicPrefix);
		GroupsResource groups = new GroupsResource(group, plug);

		ArrayList<PlugSim> plugs = new ArrayList<>();
		plugs.add(plug1);
		plugs.add(plug2);

		ArrayList<String> members = new ArrayList<>();
		members.add("a");
		members.add("b.500");

		groups.createGroup("x", members);
		
	
		assertNotNull(groups.getGroups());
	
		
	}
	
	@Test
	public void test5() throws Exception {
		
		PlugSim plug1 = new PlugSim("x");
		PlugSim plug2 = new PlugSim("b.500");
		PlugsModel plug = new PlugsModel(broker, clientId,topicPrefix);

		GroupsModel group = new GroupsModel(broker, clientId,topicPrefix);
		GroupsResource groups = new GroupsResource(group, plug);

		ArrayList<PlugSim> plugs = new ArrayList<>();
		plugs.add(plug1);
		plugs.add(plug2);

		ArrayList<String> members = new ArrayList<>();
		members.add("x");
		members.add("b.500");

		groups.createGroup("x", members);
		
	
		assertNotNull(groups.getGroups());
		
	
	}

	@Test
	public void test6() throws Exception {
		
		PlugSim plug1 = new PlugSim("x");
		PlugSim plug2 = new PlugSim("b.500");
		PlugsModel plug = new PlugsModel(broker, clientId,topicPrefix);

		GroupsModel group = new GroupsModel(broker, clientId,topicPrefix);
		GroupsResource groups = new GroupsResource(group, plug);

		ArrayList<PlugSim> plugs = new ArrayList<>();
		plugs.add(plug1);
		plugs.add(plug2);

		ArrayList<String> members = new ArrayList<>();
		members.add("x");
		members.add("b.500");

		groups.createGroup("x", members);
		groups.removeGroup("x");
		groups.createGroup("x", members);
		
	
		assertNotNull(groups.getGroups());
	}
	
	//start from here
    
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
