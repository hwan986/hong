package ece448.iot_hub;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



@Component
public class PlugsModel {
	
	private HashMap<String, Map<String,Object>> plugs = new HashMap<>();
	

	public static class MqttController {
		private final String broker;
		private final String clientId;
		private final String topicPrefix;
		
		
		private final MqttClient client;
		
	
		private final HashMap<String, String> states = new HashMap<>();
		private final HashMap<String, String> powers = new HashMap<>();
		private final HashMap<String, Object> plug = new HashMap<>();
		
	
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

		synchronized public Map<String, Object> plug() {
			return new TreeMap<>(plug);
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
				plug.put("name", nameUpdate[1]);
				plug.put("state", msg.toString());
				/*
				if(msg.toString().equals("off")){
					plug.put("state", "off");
				}
				else if(msg.toString().equals("on")){
					plug.put("state","on");
				}
				else if(msg.toString().equals("toggle")){
					String a = getState(nameUpdate[1]);
					if(a.equals("on")){
						plug.put("state","off");
					}
					else if(a.equals("off")){
						plug.put("state", "off");
					}
				}
				*/
				
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
	
	
	private final MqttController mqtt;

	
		private PlugsModel(Environment env) throws Exception {
		String broker = env.getProperty("mqtt.broker");
		String clientID = env.getProperty("mqtt.clientId");
		String topicPrefix = env.getProperty("mqtt.topicPrefix");
		this.mqtt = new MqttController(broker, clientID, topicPrefix);
		this.mqtt.start();
		
	}
            
	public void close() throws Exception {
		mqtt.close();
	}

	synchronized public List<String> getPlugs() {
		return new ArrayList<>(plugs.keySet());
	}

	/*synchronized public List<String> getPlugMembers(String plug) {
		HashSet<String> members = (HashSet<String>) plugs.get(plug);
		return (members == null)? new ArrayList<>(): new ArrayList<>(members);
	}
	*/
	

	synchronized public void removePlug(String plug) {
		plugs.remove(plug);
	}


	synchronized public void publishState(String plug, String action) {
		mqtt.publishAction(plug, action);	
	}

	
	synchronized public String getState(String plugName) {
		Map<String,Object> plugProperties = new HashMap<>();
		plugProperties.put("state",mqtt.getStates().get(plugName));
		plugs.put(plugName, plugProperties);
		return mqtt.getStates().get(plugName);
	}

	/*synchronized public boolean getPlugState(String plugName) {
		
		Map<String,Object> plugProperties = createOrGetPlug(plugName, false);
		
		return plugProperties.get("state").equals("on") ;
	}
	synchronized private Map<String,Object> createOrGetPlug(String plugName, boolean isOn){

		Map<String,Object> plugProperties = plugs.get(plugName);
		if(plugProperties == null){
			plugProperties = new HashMap<>();
			plugProperties.put("state", isOn? "on":"off");
		}
		

		plugProperties.put("name", plugName);
		
		plugs.put(plugName, plugProperties);
		return plugProperties;
	}

	   

	synchronized public void setPlug(String plugName)  {
		String action = getState3().get(plugName);
		boolean isOn = action.equals("on");
		Map<String,Object> plugProperties = createOrGetPlug(plugName,isOn);
		
		if(action.equals("on")){
			plugProperties.put("state","on");
		}
		else if(action.equals("off")){
			plugProperties.put("state","off");
		}
		else if(action.equals("toggle")){
			plugProperties.put("state", plugProperties.get("state").equals("on")? "off":"on");	
		}
	}
	*/
}