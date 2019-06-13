package br.com.rkoyanagui.core.config;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rkoyanagui.core.domain.TestPlatform;
import br.com.rkoyanagui.core.exceptions.PropertyWithIncorrentValueException;

public class ExecutionProperties {

	private static final Logger LOG = LogManager.getLogger(ExecutionProperties.class);
	
	private static String fileLocation;

	private String suiteName, customerName, testerName, platform,
			browser, webDriverPath, implicitTimeOut, reattemptsPerTest,
			waitPageLoad, webRemoteHost, webRemotePort;

	private boolean isRemote, verificarResultadoAnterior;
	
	private TestPlatform testPlatform;
	
	public ExecutionProperties() {
		if (fileLocation == null)
			setDefaultFileLocation();
		initialize();
	}

	public static void setCustomFileLocation(String fileLocation) {
		ExecutionProperties.fileLocation = fileLocation;
	}

	public static void setDefaultFileLocation() {
		ExecutionProperties.fileLocation = "./config/execution.properties";
	}

	public static String getFileLocation() {
		if (fileLocation == null)
			setDefaultFileLocation();
		return ExecutionProperties.fileLocation;
	}

	private void initialize() {
		Properties properties = null;
		try {
			properties = new PropertyUtil().loadProperties(new File(getFileLocation()).getAbsolutePath());
		} catch (Exception e) {
			InputStream file = ExecutionProperties.class.getResourceAsStream(getFileLocation());
			properties = new PropertyUtil().loadProperties(file);
		}
		suiteName = properties.getProperty("suite.name");
		customerName = properties.getProperty("customer.name");
		testerName = properties.getProperty("tester.name");
		platform = properties.getProperty("platform");
		browser = properties.getProperty("browserName");
		if (browser == null || "".equals(browser))
			browser = "chrome";
		implicitTimeOut = properties.getProperty("implicitTimeout");
		if (implicitTimeOut == null || implicitTimeOut.trim().isEmpty())
			implicitTimeOut = "10";
		waitPageLoad = properties.getProperty("web.waitPageLoad");
		if (waitPageLoad == null || waitPageLoad.trim().isEmpty())
			waitPageLoad = "30";
		webDriverPath = properties.getProperty("web.driver.path");
		webRemoteHost = properties.getProperty("web.remoteHost");
		webRemotePort = properties.getProperty("web.remotePort");
		if (webRemoteHost != null && !webRemoteHost.trim().isEmpty()) {
			isRemote = true;
		}
		try {
			testPlatform = TestPlatform.valueOf(properties.getProperty("testPlatform").toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			LOG.error("Não foi possível ler a propriedade 'testPlatform' em '" + fileLocation
					+ "'. Os valores permitidos são: " + TestPlatform.valoresPermitidos()
					+ ". Por padrão, o teste prosseguirá sobre a plataforma LOCAL ...", e);
			testPlatform = TestPlatform.LOCAL;
		}
		reattemptsPerTest = properties.getProperty("reattempts.per.test");
		if (reattemptsPerTest == null || reattemptsPerTest.trim().isEmpty())
			reattemptsPerTest = "0";
		verificarResultadoAnterior = properties.getProperty("verificarResultadoAnterior").equals("true") ? true : false;
	}

	public String getPlatform() {
		return platform;
	}
	
	public boolean isVerificarResultadoAnterior() {
		return verificarResultadoAnterior;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getTesterName() {
		return testerName;
	}

	public int getImplicitTimeOut() {
		return Integer.parseInt(this.implicitTimeOut);
	}

	public String getBrowser() {
		return this.browser;
	}

	public String getWebDriverPath() {
		if (webDriverPath == null) {
			String message = "A propriedade web.driver.path precisa receber o caminho para o executável do driver";
			LOG.warn(message);
			throw new PropertyWithIncorrentValueException(message);
		}
		return this.webDriverPath;
	}

	public int getWaitPageLoad() {
		return Integer.parseInt(this.waitPageLoad);
	}

	public int getReattemptsPerTest() {
		return Integer.parseInt(reattemptsPerTest);
	}

	public String getWebRemoteHost() {
		return webRemoteHost;
	}

	public String getWebRemotePort() {
		return webRemotePort;
	}

	public boolean isRemote() {
		return isRemote;
	}

	public TestPlatform getTestPlatform() {
		return testPlatform;
	}
}
