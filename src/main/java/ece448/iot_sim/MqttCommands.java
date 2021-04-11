package ece448.iot_sim;

import java.util.List;
//import java.util.Map;
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

		try{
			String[] tokens = topic.substring(topicPrefix.length()+1).split("/");
	

		if ((tokens.length != 3) || !tokens[0].equals("action"))
		try{
			return; // ignore unknown format
		} catch(Exception e){
			logger.info("NullPointerException");
		}	
		
		PlugSim plug = plugs.get(tokens[1]);
		if (plug == null)
		try{
			return; // no such plug
		} catch(Exception e){
			logger.info("NullPointerException");
		}

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
	} catch(Exception e){
		logger.info("NullPointerException");

	}
	}

	private static final Logger logger = LoggerFactory.getLogger(MqttCommands.class);
}
