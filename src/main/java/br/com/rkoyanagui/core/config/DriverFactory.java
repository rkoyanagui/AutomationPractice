package br.com.rkoyanagui.core.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import br.com.rkoyanagui.core.domain.TestPlatform;

public class DriverFactory {
	
	private static final Logger LOG = LogManager.getLogger(DriverFactory.class);
	
	public static WebDriver createDriverInstance()
	{
		ExecutionProperties executionProperties = new ExecutionProperties();
		
		System.setProperty( "webdriver.chrome.driver",
				new File( ProjectProperties.getInstance().getPastaDrivers()
				+ File.separator + "chromedriver.exe" ).getAbsolutePath() );
		
		TestPlatform testPlatform = executionProperties.getTestPlatform();
		DriverType driverType = DriverType.valueOf( executionProperties.getBrowser().toUpperCase() );
		URL url = null;
		switch( testPlatform )
		{
		case REMOTE:
			String host = executionProperties.getWebRemoteHost();
			String port = executionProperties.getWebRemotePort();
			String address = String.format("http://%s:%s/wd/hub", host, port );
			LOG.info( String.format( "URL: %s", address ) );
			try {
				url = new URL( address );
			}
			catch ( MalformedURLException e )
			{
				LOG.error( e );
			}
		case LOCAL:
		default:
			return driverType.getWebDriverObject( url, null );
		}
	}
}
