package br.com.rkoyanagui.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ProjectProperties
{
	private static final Logger LOG = LogManager.getLogger(ProjectProperties.class);
	private static final String FILE_LOCATION = "./config/project.properties";
	
	private ProjectProperties() {}
	
	private static ProjectProperties gerenciador;
	
	private String pastaMassa, pastaDownloads, pastaEvidencias, pastaRelatorios, pastaDrivers;
	
	public static synchronized ProjectProperties getInstance()
	{
		if ( gerenciador == null )
		{
			gerenciador = new ProjectProperties();
			Properties properties = new Properties();
			InputStream inputStream = null;
			try {
				properties = new PropertyUtil().loadProperties(
						new File( FILE_LOCATION ).getAbsolutePath() );
				initialize( properties );
			}
			catch ( Exception e )
			{
				inputStream = ProjectProperties.class.getResourceAsStream( FILE_LOCATION );
				properties = new PropertyUtil().loadProperties( inputStream );
				initialize( properties );
			}
			finally
			{
				if (inputStream != null)
				{
					try {
						inputStream.close();
					}
					catch ( IOException e )
					{
						LOG.error("Erro ao fechar o arquivo ./projeto.properties" , e);
					}
				}
			}
		}
		
		return gerenciador;
	}
	
	private static synchronized void initialize( Properties properties )
	{
		gerenciador.pastaMassa = properties.getProperty("pasta.massa");
		gerenciador.pastaDownloads = properties.getProperty("pasta.downloads");
		gerenciador.pastaEvidencias = properties.getProperty("pasta.evidencias");
		gerenciador.pastaRelatorios = properties.getProperty("pasta.relatorios");
		gerenciador.pastaDrivers = properties.getProperty("pasta.drivers");
	}

	public String getPastaMassa() {
		return pastaMassa;
	}

	public String getPastaDownloads() {
		return pastaDownloads;
	}

	public String getPastaEvidencias() {
		return pastaEvidencias;
	}

	public String getPastaRelatorios() {
		return pastaRelatorios;
	}

	public String getPastaDrivers() {
		return pastaDrivers;
	}
}
