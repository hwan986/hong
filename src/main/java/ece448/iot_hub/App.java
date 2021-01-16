package ece448.iot_hub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class App {
	@Autowired
	public App(Environment env) throws Exception {
	}
}
