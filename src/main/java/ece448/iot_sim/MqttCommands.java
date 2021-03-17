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

		String[] tokens = topic.substring(topicPrefix.length()+1).split("/");
		

		if ((tokens.length != 3) || !tokens[0].equals("action"))
			return; // ignore unknown format

	
			//topic = prefix/action/plugname/actionString 
			// topic = prefix/update/plugname/Powercon
			//topic = prefix/update/plugname/Powerstamp
		

		// prefix/action/plugname/actionString
		// iot_ece448/action/xx/on
		PlugSim plug = plugs.get(tokens[1]);
		if (plug == null)
			return; // no such plug

		String action = tokens[2];

		logger.info("action {}", action);
			
		if(action.equals("on"))
		{
			plug.switchOn();
			
		   
		}
		if(action.equals("off"))
		{
			plug.switchOff();
			
		     
		}
		if(action.equals("toggle"))
		{
			plug.toggle();
			
		     
		}
		
	}



	private static final Logger logger = LoggerFactory.getLogger(MqttCommands.class);
}
