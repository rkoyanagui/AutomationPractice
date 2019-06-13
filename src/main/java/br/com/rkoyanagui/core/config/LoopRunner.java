package br.com.rkoyanagui.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoopRunner
{
	private static final Logger LOG = LogManager.getLogger(LoopRunner.class);
	
	public void executar( String[] classesParaExecutar )
	{
		executar( classesParaExecutar, 0 );
	}
	
	public void executar( String[] classesParaExecutar, int numeroDeThreads )
	{	
		// Se qtdLicencasDisponiveis <= 0, então lê do arquivo 'loop.properties'.
		if ( numeroDeThreads <= 0 )
		{
			numeroDeThreads = new LoopProperties().numeroDeLicencas();
		}
		
		boolean loop = true;
		boolean pararAgora = false;

		while (loop && !pararAgora)
		{
			new TestNgSuiteRunner().execute( classesParaExecutar, numeroDeThreads );
			loop = new LoopProperties().isLoop();
			pararAgora = new LoopProperties().pararAgora();
			LOG.info( String.format( "loop = %s | pararAgora = %s", loop, pararAgora ) );
		}
	}
}
