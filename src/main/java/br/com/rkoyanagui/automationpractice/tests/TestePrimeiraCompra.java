package br.com.rkoyanagui.automationpractice.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import br.com.rkoyanagui.core.config.GerenciadorDeContexto;
import br.com.rkoyanagui.core.domain.TestBase;
import br.com.rkoyanagui.core.domain.Usuario;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;

@CucumberOptions(                 
		features = "src/test/resources/features/PrimeiraCompra.feature", 
		glue = "br.com.rkoyanagui.automationpractice.steps",
        plugin = { "pretty", "json:target/cucumber.json" }, 
        tags = { "~@ignore" }, strict = false)

public class TestePrimeiraCompra extends TestBase
{
	private static final Logger LOG = LogManager.getLogger(TestePrimeiraCompra.class);
	private static final String idCenario = "00001";
	
	@Test(	groups = { "transacional", "compra", "cadastro" },
			description = idCenario,
			retryAnalyzer = br.com.rkoyanagui.core.listeners.RetryAnalyzer.class,
			dataProvider = "features")
	public void teste( XmlTest xmlTest, CucumberFeatureWrapper cucumberFeature )
	{
		Usuario usuario = buscaUsuario(idCenario);
		GerenciadorDeContexto.setUsuario(usuario);
		LOG.info("Usuario: " + usuario);
		instanciaDriver();
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}
}
