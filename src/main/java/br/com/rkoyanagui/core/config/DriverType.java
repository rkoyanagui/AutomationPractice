package br.com.rkoyanagui.core.config;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum DriverType implements DriverSetup
{
	FIREFOX
	{
		public WebDriver getWebDriverObject( URL url, DesiredCapabilities capabilities )
		{
			FirefoxOptions options = new FirefoxOptions();
			if ( capabilities != null )
			{
				options.merge( capabilities );
			}
			options.setHeadless( HEADLESS );
			
			if ( url != null )
			{
				return new RemoteWebDriver( url, options );
			}
			else
			{
				return new FirefoxDriver( options );
			}
		}
	},
	
	CHROME
	{
		public WebDriver getWebDriverObject( URL url, DesiredCapabilities capabilities )
		{
			HashMap<String, Object> chromePreferences = new HashMap<>();
			chromePreferences.put( "profile.password_manager_enabled", false );

			ChromeOptions options = new ChromeOptions();
			if ( capabilities != null )
			{
				options.merge( capabilities );
			}
			options.setHeadless( HEADLESS );
			options.addArguments( "--no-default-browser-check" );
			options.setExperimentalOption( "prefs", chromePreferences );
			
			if ( url != null )
			{
				return new RemoteWebDriver( url, options );
			}
			else
			{
				return new ChromeDriver( options );
			}
		}
	};

	public final static boolean HEADLESS = Boolean.getBoolean( "headless" );

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
