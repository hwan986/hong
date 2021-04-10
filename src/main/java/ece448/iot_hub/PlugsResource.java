package ece448.iot_hub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
//import ece448.iot_hub.PlugsModel.MqttController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlugsResource {

	private final PlugsModel plugs;
	//private final MqttController mqtt;

	public PlugsResource(PlugsModel plugs) {
		this.plugs = plugs;
	//	this.mqtt = mqtt;
	}
	
	@GetMapping("/api/plugs")
	public Collection<Object> getPlugs() throws Exception {
		ArrayList<Object> ret = new ArrayList<>();
		for (String plug: plugs.getPlugs()) {
			ret.add(makePlug(plug, false));
		}
		logger.info("Plugs: {}", ret);
		return ret;
	}

	@GetMapping("/api/plugs/{plug:.+}")
	public Object getPlug(
		@PathVariable("plug") String plug,
		@RequestParam(value = "action", required = false) String action) throws Exception  {
		if (action == null) {
			Object ret;
			ret = makePlug(plug, plugs.getPlugState(plug));
			logger.info("Plug {}: {}", plug, ret);
			return ret;
		}
		plugs.updateState(plug, action);
		plugs.setPlug(plug, action);
		// modify code below to control plugs by publishing messages to MQTT broker
		//List<String> members = plugs.getPlugMembers(plug);
		logger.info("Plug {}: action {}, {}", plug, action);
		return makePlug(plug, plugs.getPlugState(plug));// not null
	}

	@PostMapping("/api/plugs/{plug:.+}")
	public void createPlug(
		@PathVariable("plug") String plug,
		@RequestBody String action) throws Exception {
		plugs.setPlug(plug, action);
		logger.info("Plug {}: created {},{}", plug, action);
	}

	@DeleteMapping("/api/plugs/{plug:.+}")
	public void removePlug(
		@PathVariable("plug") String plug) {
		plugs.removePlug(plug);
		logger.info("Plug {}: removed", plug);
	}

	protected Object makePlug(String plug, boolean isOn) {
		// modify code below to include plug states
		HashMap<String, Object> ret = new HashMap<>();
		
		ret.put("name", plug);
		//ret.put("state","off");
		ret.put("state", isOn ? "on" : "off");
		if(plug.indexOf(".") != -1)
		{
			ret.put("power", Integer.parseInt(plug.split("\\.")[1]));
		}
		ret.put("power", "0");
		return ret;
	}

	/*static String getStates1() throws Exception {
		TreeMap<String, String> states = new TreeMap<>();
		for (String name: plugs)
		{
			Map<String, Object> plug = mapper.readValue(getHub("/api/plugs/" + name),
				new TypeReference<Map<String, Object>>() {});
			if (!name.equals((String)plug.get("name")))
				throw new Exception("invalid name " + name);
			states.put(name, "off".equals((String)plug.get("state"))? "0": "1");
		}
		String ret = String.join("", states.values());
		logger.debug("GradeP4: getState1 {}", ret);
		return ret;
	}
	*/

	private static final Logger logger = LoggerFactory.getLogger(PlugsResource.class);	
}