package br.com.rkoyanagui.core.config;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class LocalProperties {

	private static String fileLocation;

	private String serverHost, serverPort;

	public LocalProperties() {
		if (fileLocation == null)
			setDefaultFileLocation();
		initialize();
	}

	public static void setCustomFileLocation(String fileLocation) {
		LocalProperties.fileLocation = fileLocation;
	}

	public static void setDefaultFileLocation() {
		LocalProperties.fileLocation = "./config/local.properties";
	}

	public static String getFileLocation() {
		return LocalProperties.fileLocation;
	}

	private void initialize() {
		Properties properties = null;
		try {
			properties = new PropertyUtil().loadProperties(new File(getFileLocation()).getAbsolutePath());
		} catch (Exception e) {
			InputStream file = LocalProperties.class.getResourceAsStream(getFileLocation());
			properties = new PropertyUtil().loadProperties(file);
		}
		this.serverHost = properties.getProperty("server.host");
		this.serverPort = properties.getProperty("server.port");
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerAddress(String serverAddress) {
		this.serverHost = serverAddress;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
}
