package br.com.rkoyanagui.core.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoopProperties {
	
	private static final Logger LOG = LogManager.getLogger(LoopProperties.class);

	private boolean loop, pararAgora;
	private int numLicencas;

	public LoopProperties() {
		try {
			BufferedReader oB = new BufferedReader(
								new FileReader("./config/loop.properties"));

			while (oB.ready()) {
				loop = oB.readLine().replace("loop=", "").trim().equals("true");
				pararAgora = oB.readLine().replace("pararAgora=", "").trim().equals("true");
				numLicencas = Integer.parseInt( oB.readLine().replace("numLicencas=", "").trim() );
			}
			oB.close();
		} catch (IOException e) {
			LOG.error( e );
		}

	}

	public boolean isLoop() {
		return loop;
	}
	
	public boolean pararAgora() {
		return pararAgora;
	}
	
	public int numeroDeLicencas()
	{
		return numLicencas;
	}

}
