package ece448.iot_hub;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class PlugsModel implements AutoCloseable {
	

	
	private HashMap<String, Map<String,Object>> plugs = new HashMap<>();
	private final MqttController mqtt;
	
		public PlugsModel(String broker, String clientID, String topicPrefix) throws Exception {
			
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

		synchronized public String getPower(String plugName) {
			Map<String,Object> plugProperties = new HashMap<>();
			plugProperties.put("power",mqtt.getPowers().get(plugName));
			plugs.put(plugName, plugProperties);
			return mqtt.getPowers().get(plugName);
		}
	
		@Override
		public void close() throws Exception {
			mqtt.close();
		}	
}