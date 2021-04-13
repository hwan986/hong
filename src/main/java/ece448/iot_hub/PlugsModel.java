package ece448.iot_hub;


import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



@Component
public class PlugsModel {
	
	private HashMap<String, Map<String,Object>> plugs = new HashMap<>();
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
	/*
	synchronized public void setPlugMembers(String plug, List<String> members) {
		Map<String,Object> plugProperties = new HashMap<>();
		plugProperties.put("name",members);
		plugs.put(plug, plugProperties); 
	}
	synchronized public List<String> getPlugMembers(String plug) {
		HashSet<String> members = (HashSet<String>) plugs.get(plug);
		return (members == null)? new ArrayList<>(): new ArrayList<>(members);
	}
	
	

	synchronized public void removePlug(String plug) {
		plugs.remove(plug);
	}
*/

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