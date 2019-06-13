package br.com.rkoyanagui.core.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class LocalDriverManager
{
	private static List<WebDriver> webDriverThreadPool = Collections.synchronizedList(new ArrayList<WebDriver>());
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	 
    public static WebDriver getDriver()
    {
        return webDriver.get();
    }
 
    public static void setWebDriver(WebDriver driver)
    {
        webDriver.set(driver);
        webDriverThreadPool.add(driver);
    }
    
    public static List<WebDriver> getWebDriverThreadPool()
    {
    	return webDriverThreadPool;
    }
}
