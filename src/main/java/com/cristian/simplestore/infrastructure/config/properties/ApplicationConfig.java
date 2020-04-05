package com.cristian.simplestore.infrastructure.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {
	
	@Value("${application.address}")
	private String path;
	
	@Value("${server.port}")
	private String port;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public String getFullPath() {
		return path + ":" + port;
	}
}
