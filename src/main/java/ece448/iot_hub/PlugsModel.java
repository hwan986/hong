package ece448.iot_hub;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;



@Component
public class PlugsModel {
	private HashMap<String, HashSet<String>> plugs = new HashMap<>();

	synchronized public List<String> getPlugs() {
		return new ArrayList<>(plugs.keySet());
	}

	synchronized public List<String> getPlugMembers(String plug) {
		HashSet<String> members = plugs.get(plug);
		return (members == null)? new ArrayList<>(): new ArrayList<>(members);
	}

	synchronized public void setPlugMembers(String plug, List<String> members) {
		plugs.put(plug, new HashSet<>(members));
	}

	synchronized public void removePlug(String plug) {
		plugs.remove(plug);
	}

	public static class MqttController {
		private final String broker;
		private final String clientId;
		private final String topicPrefix;
		
		private final MqttClient client;
	
		private final HashMap<String, String> states = new HashMap<>();
		private final HashMap<String, String> powers = new HashMap<>();
	
		public MqttController(String broker, String clientId,
			String topicPrefix) throws Exception {
			this.broker = broker;
			this.clientId = clientId;
			this.topicPrefix = topicPrefix;
			this.client = new MqttClient(broker, clientId, new MemoryPersistence());
		}
	
		public void start() throws Exception {
			MqttConnectOptions opt = new MqttConnectOptions();
			opt.setCleanSession(true);
			client.connect(opt);
			
			client.subscribe(topicPrefix+"/update/#", this::handleUpdate);
	
			logger.info("MqttCtl {}: {} connected", clientId, broker);
		}
	
		public void close() throws Exception {
			client.disconnect();
			logger.info("MqttCtl {}: disconnected", clientId);
		}
	
		synchronized public void publishAction(String plugName, String action) {
			String topic = topicPrefix+"/action/"+plugName+"/"+action;
			try
			{
				client.publish(topic, new MqttMessage());
			}
			catch (Exception e)
			{
				logger.error("MqttCtl {}: {} fail to publish", clientId, topic);
			}
		}
	
		synchronized public String getState(String plugName) {
			return states.get(plugName);
		}
	
		synchronized public String getPower(String plugName) {
			return powers.get(plugName);
		}
	
		synchronized public Map<String, String> getStates() {
			return new TreeMap<>(states);
		}
	
		synchronized public Map<String, String> getPowers() {
			return new TreeMap<>(powers);
		}
	
		synchronized protected void handleUpdate(String topic, MqttMessage msg) {
			logger.debug("MqttCtl {}: {} {}", clientId, topic, msg);
	
			String[] nameUpdate = topic.substring(topicPrefix.length()+1).split("/");
			if ((nameUpdate.length != 3) || !nameUpdate[0].equals("update"))
				return; // ignore unknown format
	
			switch (nameUpdate[2])
			{
			case "state":
				states.put(nameUpdate[1], msg.toString());
				break;
			case "power":
				powers.put(nameUpdate[1], msg.toString());
				break;
			default:
				return;
			}
		}
	
		private static final Logger logger = LoggerFactory.getLogger(MqttController.class);
		}
	
	private static final String broker = "tcp://127.0.0.1";
	private static final String topicPrefix = System.currentTimeMillis()+"/grade_p4/iot_ece448";

	private final MqttController mqtt;

	private PlugsModel() throws Exception {
		this.mqtt = new MqttController(broker, "grader/iot_sim", topicPrefix);
		this.mqtt.start();
	}
		
	
	public void close() throws Exception {
		mqtt.close();
	}
}