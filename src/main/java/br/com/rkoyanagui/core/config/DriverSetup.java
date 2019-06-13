package br.com.rkoyanagui.core.config;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverSetup
{
    WebDriver getWebDriverObject( URL url, DesiredCapabilities capabilities );
}