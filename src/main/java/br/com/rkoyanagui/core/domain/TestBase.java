package br.com.rkoyanagui.core.domain;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlTest;

import br.com.rkoyanagui.core.config.DriverFactory;
import br.com.rkoyanagui.core.config.ExecutionProperties;
import br.com.rkoyanagui.core.config.GerenciadorDeContexto;
import br.com.rkoyanagui.core.config.LocalDriverManager;
import br.com.rkoyanagui.core.config.LoopProperties;
import br.com.rkoyanagui.core.exceptions.UserSearchException;
import br.com.rkoyanagui.core.storage.LeitorDaPlanilhaUsuarios;
import br.com.rkoyanagui.core.storage.RelatorioUtils;
import cucumber.api.testng.TestNGCucumberRunner;

public class TestBase
{
	private static final Logger LOG = LogManager.getLogger(TestBase.class);

	protected TestNGCucumberRunner testNGCucumberRunner;
	
	protected List<Usuario> usuarios;
	protected ExecutionProperties execProperties = new ExecutionProperties();
	
	@BeforeClass( alwaysRun = true )
	public void setUpClass( XmlTest xmlTest ) throws Exception
	{
		testNGCucumberRunner = new TestNGCucumberRunner( this.getClass() );
	}
	
	@BeforeMethod( alwaysRun = true, firstTimeOnly = true )
	public void beforeMethod()
	{
		verificaSeDevePararAgora();
		GerenciadorDeContexto.setAtualTentativa(0);
	}

	@DataProvider
	public Object[][] features()
	{
		return testNGCucumberRunner.provideFeatures();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception
	{
		testNGCucumberRunner.finish();
	}

	protected Usuario buscaUsuario( String idCenario ) throws SkipException
	{
		Usuario usuario = null;
		LOG.info("---- Tentativa: " + GerenciadorDeContexto.getAtualTentativa());
		if (GerenciadorDeContexto.getAtualTentativa() == 0) {
			try {
//				GerenciadorDeContexto.setQtdeUsuarios(0);
				usuarios = null;
				usuarios = LeitorDaPlanilhaUsuarios.buscaUsuarios( idCenario );
//				GerenciadorDeContexto.setQtdeUsuarios(usuarios.size());
//				LOG.info("---- qtdeUsuarios: " + GerenciadorDeContexto.getQtdeUsuarios());
				verificaSeDeveRodar( idCenario );
			}
			catch (UserSearchException e)
			{
				LOG.error(e.getMessage());
				throw new SkipException(e.getMessage());
			}
		}
		try {
			usuario = usuarios.get(GerenciadorDeContexto.getAtualTentativa());
		}
		catch (IndexOutOfBoundsException e)
		{
			String mensagem = "Não há mais Usuários para continuar a tentar o teste '" + idCenario + "' ...";
			LOG.error(mensagem);
			throw new SkipException(mensagem);
		}
		return usuario;
	}
	
	private boolean verificaSeDeveRodar( String id ) throws SkipException
	{
		if ( execProperties.isVerificarResultadoAnterior() )
		{
			int status = 2;
			try {
				status = RelatorioUtils.seraQueJaDeuCerto( id );
			}
			catch ( Exception e )
			{
				String mensagem = "---- Erro ao ler o relatório ----";
				LOG.error( mensagem );
				throw new SkipException( mensagem );
			}
			
			if ( status == 2 )
			{
				String mensagem = "---- Não foi encontrado no relatório o ID '" + id + "' ----";
				LOG.info( mensagem );
				throw new SkipException( mensagem );
			}
			else if ( status == 1 )
			{
				String mensagem = "---- Ja houve um teste com sucesso para o ID '" + id + "' ----";
				LOG.info( mensagem );
				throw new SkipException( mensagem );
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	protected void instanciaDriver()
	{
		WebDriver driver = null;
        
        try {
        	driver = DriverFactory.createDriverInstance();
		}
        catch (Exception e)
        {
        	String message = "Não foi possível iniciar o Driver ...";
			LOG.error(message, e);
			// pula o teste se ocorrer erro na instanciacao do driver
			throw new SkipException(e.getMessage());
		}
        
        if ( driver == null )
        {
        	String message = "Não foi possível iniciar o Driver ...";
        	LOG.error( message );
        	throw new SkipException( message );
        }
        
        LocalDriverManager.setWebDriver( driver );
	}
	
	private void verificaSeDevePararAgora()
	{
		if ( new LoopProperties().pararAgora() )
		{
			String mensagem = String.format( "---- pararAgora = TRUE. Pulando o teste %s ----",
					this.getClass().getSimpleName() );
			LOG.error( mensagem );
			throw new SkipException( mensagem );
		}
	}
}
