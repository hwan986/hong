package ece448.iot_hub;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class App {	
	@Bean
	public PlugsModel plugModel(Environment env) throws Exception{
		String broker = env.getProperty("mqtt.broker");
		String clientID = env.getProperty("mqtt.clientId");
		String topicPrefix = env.getProperty("mqtt.topicPrefix");
		PlugsModel plugs = new PlugsModel(broker, clientID, topicPrefix);
		logger.info("MqttClient {} connected: {}", clientID, broker);
		return plugs;
	}
    
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);
			
}

/*
@SpringBootApplication
public class App {
}


/*
private final String broker1;
    private final String clientId1;
	private final String topicPrefix1;
	public App(String broker1, String clientId1,String topicPrefix1) throws Exception{
		this.broker1 = broker1;
		this.clientId1 = clientId1;
		this.topicPrefix1 = topicPrefix1;
		broker1 = env.getProperty("mqtt.broker");
		clientId1 = env.getProperty("mqtt.clientId");
		topicPrefix1 = env.getProperty("mqtt.clientId");
		synchronized public String getState() {
        return broekr1
    }
	}
*/
	/*
	@Bean (destroyMethod = "disconnect")
	public MqttClient mqttClient(Environment env) throws Exception{
		//   "mqttBroker": "tcp://127.0.0.1",
 		// "mqttClientId": "iot_hub",
		String broker = env.getProperty("mqtt.broker");
		String clientID = env.getProperty("mqtt.clientId"); //"iot_hub"
		
		MqttClient mqtt = new MqttClient(broker, clientID, new MemoryPersistence());
		mqtt.connect();
		logger.info("MqttClient {} connected: {}", clientID, broker);
		return mqtt;
	}

	*/
