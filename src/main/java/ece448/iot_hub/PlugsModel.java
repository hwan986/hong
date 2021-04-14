package ece448.iot_hub;


import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class PlugsModel implements AutoCloseable {
	

	
	private HashMap<String, Map<String,Object>> plugs = new HashMap<>();
	private final MqttController mqtt;
	
		public PlugsModel(String broker, String clientID, String topicPrefix) throws Exception {
			//String broker = env.getProperty("mqtt.broker");
			//String clientID = env.getProperty("mqtt.clientId");
			//String topicPrefix = env.getProperty("mqtt.topicPrefix");
			this.mqtt = new MqttController(broker, clientID, topicPrefix);
			this.mqtt.start();	
		}

		synchronized public List<String> getPlugs() {
			return new ArrayList<>(plugs.keySet());
		}
	
		synchronized public void setPlugMembers(String plug, List<String> members) {
			Map<String,Object> plugProperties = new HashMap<>();
			plugProperties.put("name",members);
			plugs.put(plug, plugProperties); 
		}

		/*
		synchronized public List<String> getPlugMembers(String plug) {
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
	
		@Override
		public void close() throws Exception {
			mqtt.close();
		}	
}