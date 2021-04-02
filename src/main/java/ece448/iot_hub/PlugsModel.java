package ece448.iot_hub;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
}