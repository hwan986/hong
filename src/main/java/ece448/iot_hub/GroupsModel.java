package ece448.iot_hub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GroupsModel implements AutoCloseable{
	private HashMap<String, HashSet<String>> groups = new HashMap<>();
	private MqttController mqtt;

	public GroupsModel(String broker, String clientID, String topicPrefix) throws Exception{
		this.mqtt = new MqttController(broker, clientID, topicPrefix);
		mqtt.start();
	}
	@Override
	public void close() throws Exception{
		mqtt.close();
	}

	synchronized public List<String> getGroups() {
		return new ArrayList<>(groups.keySet());
	}

	synchronized public List<String> getGroupMembers(String group) {
		HashSet<String> members = groups.get(group);
		return (members == null)? new ArrayList<>(): new ArrayList<>(members);
	}

	synchronized public void setGroupMembers(String group, List<String> members) {
		groups.put(group, new HashSet<>(members));
	}

	synchronized public void removeGroup(String group) {
		groups.remove(group);
	}
	
	synchronized public void publishState(String plug, String action) {
		mqtt.publishAction(plug, action);	
	}
	

	synchronized public String getState(String plugName) {
		//Map<String,Object> plugProperties = new HashMap<>();
		//plugProperties.put("state",mqtt.getStates().get(plugName));
		//plugs.put(plugName, plugProperties);
		return mqtt.getStates().get(plugName);
	}

	synchronized public String getPower(String plugName) {
		//HashSet<String> ha = new HashSet<String>();
		//Map<String,Object> plugProperties = new HashMap<>();
		//plugProperties.put("state",mqtt.getStates().get(plugName));
		//plugs.put(plugName, plugProperties);
		return mqtt.getPowers().get(plugName);
	}
}