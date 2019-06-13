package br.com.rkoyanagui.automationpractice.runners;

import br.com.rkoyanagui.core.config.LoopRunner;

public class Main
{
	public static void main( String[] args )
	{
		String [] testes = {
				br.com.rkoyanagui.automationpractice.tests.TestePrimeiraCompra.class.getCanonicalName(),
		};

		LoopRunner loopRunner = new LoopRunner();
		loopRunner.executar( testes );
	}
}
