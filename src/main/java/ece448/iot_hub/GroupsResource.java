package ece448.iot_hub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class GroupsResource {

	private final GroupsModel groups;
	private final PlugsResource plugs;

	public GroupsResource(GroupsModel groups, PlugsModel plugs) {
		this.groups = groups;
		this.plugs = new PlugsResource(plugs);
	}
	
	@GetMapping("/api/groups")
	public Collection<Object> getGroups() throws Exception {
		ArrayList<Object> ret = new ArrayList<>();
		for (String group: groups.getGroups()) {
			ret.add(makeGroup(group));
		}
		logger.info("Groups: {}", ret);
		return ret;
	}

	@GetMapping("/api/groups/{group}")
	public Object getGroup(
		@PathVariable("group") String group,
		@RequestParam(value = "action", required = false) String action) throws Exception {
			

		if (action == null) {
			Object ret = makeGroup(group);
			logger.info("Group {}: {}", group, ret);
			return ret;
		} 

		// modify code below to control plugs by publishing messages to MQTT broker
		List<String> members = groups.getGroupMembers(group);
		
		for(String member: members){
			Object ret = new HashMap<>();
			ret = plugs.getPlug(member, action);
			logger.info("Group {}: {}", group, member);
		}
		
		

		logger.info("Group {}: action {}, {}", group, action, members);
		return makeGroup(group);
	}

	@PostMapping("/api/groups/{group}")
	public void createGroup(
		@PathVariable("group") String group,
		@RequestBody List<String> members) {
		groups.setGroupMembers(group, members);

		logger.info("Group {}: created {}", group, members);
	}

	@DeleteMapping("/api/groups/{group}")
	public void removeGroup(
		@PathVariable("group") String group) {
		groups.removeGroup(group);
		logger.info("Group {}: removed", group);
	}

	protected Object makeGroup(String group) {
		// modify code below to include plug states
		HashMap<String, Object> ret = new HashMap<>();
		List<Map<String, Object>> plugMembers = new ArrayList<Map<String,Object>>();
		ret.put("name", group);

		List<String> members = groups.getGroupMembers(group);
		for(String member: members){
			plugMembers.add(plugs.makePlug(member) );
		}
		ret.put("members", plugMembers);
		logger.info("Group {}: member{}, {} ", group, members, ret);

		return ret;
	}

	private static final Logger logger = LoggerFactory.getLogger(GroupsResource.class);	
}