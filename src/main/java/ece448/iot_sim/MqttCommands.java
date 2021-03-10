package ece448.iot_sim;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MqttCommands {

	// Use a map so we can search plugs by name.
	private final TreeMap<String, PlugSim> plugs = new TreeMap<>();
	private final String topicPrefix;
	


	public MqttCommands(List<PlugSim> plugs, String topicPrefix) {
	
		
		
		for (PlugSim plug: plugs)
			this.plugs.put(plug.getName(), plug);
		this.topicPrefix = topicPrefix;
		
	}

	public String getTopic(){
		return topicPrefix+"/action/#";
	}


	
	public void handleMessage(String topic, MqttMessage msg) {
		

		logger.info("MqttCmd {}", topic);
		// switch on/off/toggle here

		
	}

	protected String listPlugs() {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><body>");
		for (String plugName: plugs.keySet())
		{
			sb.append(String.format("<p><a href='/%s'>%s</a></p>",
				plugName, plugName));
		}
		sb.append("</body></html>");

		return sb.toString();
	}

	protected String report(PlugSim plug) {
		String name = plug.getName();
		return String.format("<html><body>"
			+"<p>Plug %s is %s.</p>"
			+"<p>Power reading is %.3f.</p>"
			+"<p><a href='/%s?action=on'>Switch On</a></p>"
			+"<p><a href='/%s?action=off'>Switch Off</a></p>"
			+"<p><a href='/%s?action=toggle'>Toggle</a></p>"
			+"</body></html>",
			name,
			plug.isOn()? "on": "off",
			plug.getPower(), name, name, name);
	}

	private static final Logger logger = LoggerFactory.getLogger(MqttCommands.class);
}
