package br.com.rkoyanagui.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rkoyanagui.core.exceptions.FrameworkException;

public class PropertyUtil {

	public static Logger LOG = LogManager.getLogger(PropertyUtil.class);;

	public Properties loadProperties(String fileName) {
		Properties props = new Properties();
		try {
			InputStream is = new FileInputStream(new File(fileName));
			props.load(is);
			return props;
		} catch (IOException e) {
			throw new FrameworkException("Falha ao carregar o arquivo de Properties: " + fileName, e);
		}
	}

	public Properties loadProperties(InputStream fileName) {
		Properties props = new Properties();
		try {
			InputStream is = fileName;
			props.load(is);
		} catch (IOException e) {
			throw new FrameworkException("Falha ao carregar o arquivo de Properties: " + fileName, e);
		}
		return props;
	}
}
