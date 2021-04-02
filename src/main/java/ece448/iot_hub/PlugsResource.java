package ece448.iot_hub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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

	public PlugsResource(PlugsModel plugs) {
		this.plugs = plugs;
	}
	
	@GetMapping("/api/plugs/{plugs:.+}")
	public Collection<Object> getPlugs() throws Exception {
		ArrayList<Object> ret = new ArrayList<>();
		for (String plug: plugs.getPlugs()) {
			ret.add(makePlug(plug));
		}
		logger.info("Plugs: {}", ret);
		return ret;
	}

	@GetMapping("/api/plugs/{plug:.+}")
	public Object getPlug(
		@PathVariable("plug") String plug,
		@RequestParam(value = "action", required = false) String action) {
		if (action == null) {
			Object ret = makePlug(plug);
			logger.info("Plug {}: {}", plug, ret);
			return ret;
		}

		// modify code below to control plugs by publishing messages to MQTT broker
		List<String> members = plugs.getPlugMembers(plug);
		logger.info("Plug {}: action {}, {}", plug, action, members);
		return null;
	}

	@PostMapping("/api/plugs/{plug:.+}")
	public void createPlug(
		@PathVariable("plug") String plug,
		@RequestBody List<String> members) {
		plugs.setPlugMembers(plug, members);
		logger.info("Plug {}: created {}", plug, members);
	}

	@DeleteMapping("/api/plugs/{plug:.+}")
	public void removePlug(
		@PathVariable("plug") String plug) {
		plugs.removePlug(plug);
		logger.info("Plug {}: removed", plug);
	}

	protected Object makePlug(String plug) {
		// modify code below to include plug states
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("name", plug);
		ret.put("members", plugs.getPlugMembers(plug));
		return ret;
	}

	private static final Logger logger = LoggerFactory.getLogger(PlugsResource.class);	
}