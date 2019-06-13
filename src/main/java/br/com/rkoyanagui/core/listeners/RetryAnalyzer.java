package br.com.rkoyanagui.core.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import br.com.rkoyanagui.core.config.ExecutionProperties;
import br.com.rkoyanagui.core.config.GerenciadorDeContexto;

public class RetryAnalyzer implements IRetryAnalyzer {
	
	private static final Logger LOG = LogManager.getLogger(RetryAnalyzer.class);
	private static final ExecutionProperties executionProperties = new ExecutionProperties();
	private static final int LIMITE_DE_TENTATIVAS = executionProperties.getReattemptsPerTest();

	@Override
	public boolean retry(ITestResult result)
	{
		if (result.getMethod().isTest())
		{
			LOG.info("---- Avaliando retry, contador = " + GerenciadorDeContexto.getAtualTentativa() + " ----");
			
//			if (GerenciadorDeContexto.getAtualTentativa() + 1 < GerenciadorDeContexto.getQtdeUsuarios())
//			{
//				LOG.info("---- Ha usuarios para tentar de novo ----");
				
				if (GerenciadorDeContexto.getAtualTentativa() < LIMITE_DE_TENTATIVAS)
				{
					LOG.info("---- Ha tentativas remanescentes ----");
					GerenciadorDeContexto.incrementAtualTentativa();
					return true;
				}
				else
				{
					LOG.info("---- NAO ha tentativas remanescentes ----");
					GerenciadorDeContexto.setAtualTentativa(0);
					return false;
				}
//			}
//			else
//			{
//				LOG.info("---- NAO ha usuarios para tentar de novo ----");
//				GerenciadorDeContexto.setAtualTentativa(0);
//				return false;
//			}
		}
		else
		{
			return false;
		}
		
	}
}
