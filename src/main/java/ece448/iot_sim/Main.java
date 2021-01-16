package ece448.iot_sim;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	}

	@Override
	public void close() throws Exception {
		http.close();
	}

	private final JHTTP http;

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
}
