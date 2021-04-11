package ece448.iot_hub;


//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;

@SpringBootApplication
public class App {
	
	
}
/*@SpringBootApplication
public class App {
	
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
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	
	
}
*/