package ece448.iot_sim;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ece448.iot_sim.http_server.JHTTP;

public class Main implements AutoCloseable {
	public static void main(String[] args) throws Exception {
		// load configuration file
		String configFile = args.length > 0 ? args[0] : "simConfig.json";
		SimConfig config = mapper.readValue(new File(configFile), SimConfig.class);
		logger.info("{}: {}", configFile, mapper.writeValueAsString(config));

		try (Main m = new Main(config))
		{
			// loop forever
			for (;;)
			{
				Thread.sleep(60000);
			}
		}
	}

	public Main(SimConfig config) throws Exception {
		// create plugs
		ArrayList<PlugSim> plugs = new ArrayList<>();
		for (String plugName: config.getPlugNames()) {
			plugs.add(new PlugSim(plugName));
		}

		// start power measurements
		MeasurePower measurePower = new MeasurePower(plugs);
		measurePower.start();

		// start HTTP commands
		this.http = new JHTTP(config.getHttpPort(), new HTTPCommands(plugs));
		this.http.start();

		// start MQTT client
		this.mqtt = new MqttClient(config.getMqttBroker(), config.getMqttClientId(), new MemoryPersistence());
		this.mqtt.connect();

		MqttCommands mqttCmd = new MqttCommands(plugs, config.getMqttTopicPrefix());
		logger.info("MQtt subscribe to {}", mqttCmd.getTopic());

		this.mqtt.subscribe(mqttCmd.getTopic(),(topic, msg)-> {mqttCmd.handleMessage(topic, msg);});

		MqttUpdates mqttUpd = new MqttUpdates(config.getMqttTopicPrefix());
		for(PlugSim plug: plugs){
			plug.addObserver((name, key, value) -> {
				try{
					mqtt.publish(mqttUpd.getTopic(name, key), mqttUpd.getMessage(value));
				} catch (Exception e){
				logger.error("fail to publish {} {} {}", name, key, value, e);
				}
			});
		}
	}

	@Override
	public void close() throws Exception {
		http.close();
		mqtt.disconnect();
	}

	private final JHTTP http;
	private final MqttClient mqtt;

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
}
