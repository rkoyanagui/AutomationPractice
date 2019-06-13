package br.com.rkoyanagui.core.listeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import br.com.rkoyanagui.core.config.ExecutionProperties;
import br.com.rkoyanagui.core.config.LocalDriverManager;
import br.com.rkoyanagui.core.config.ProjectProperties;
import br.com.rkoyanagui.core.storage.RelatorioUtils;

public class ScreenshotListener extends TestListenerAdapter
{
	private static final Logger LOG = LogManager.getLogger(ScreenshotListener.class);
	
	@Override
    public void onTestFailure( ITestResult testResult )
	{
		String idCompleto = idCompleto( testResult );
		String nomeCenario = nomeDoCenario( testResult );
		LOG.info( "Teste " + idCompleto + "_" + nomeCenario + " falhou ..." );
		finalizaTesteFeito( testResult, idCompleto, nomeCenario, false );
    }
    
    @Override
    public void onTestSkipped( ITestResult testResult )
    {
    	String idCompleto = idCompleto( testResult );
    	String nomeCenario = nomeDoCenario( testResult );
		LOG.info( "Teste " + idCompleto + "_" + nomeCenario + " foi pulado ..." );
    }
    
    @Override
    public void onTestSuccess( ITestResult testResult )
    {
    	String idCompleto = idCompleto( testResult );
    	String nomeCenario = nomeDoCenario( testResult );
		LOG.info( "Teste " + idCompleto + "_" + nomeCenario + " teve sucesso ..." );
		finalizaTesteFeito( testResult, idCompleto, nomeCenario, true );
		
		moveCapturaDeTelaDeCenarioReprocessadoComSucesso( idCompleto + "_" + nomeCenario );
    }
    
    private void moveCapturaDeTelaDeCenarioReprocessadoComSucesso( String idNome )
    {
    	LocalDateTime tempo = LocalDateTime.now();
    	String data = tempo.format( DateTimeFormatter.ofPattern( "uuuu_MM_dd" ) );
    	String pastaEvidencias = ProjectProperties.getInstance().getPastaEvidencias();
    	File origem = new File( pastaEvidencias + File.separator + data
    			+ File.separator + "failure" + File.separator + idNome );
    	File destino = new File( pastaEvidencias + File.separator + data
    			+ File.separator + "reprocessed" + File.separator + idNome );
    	if ( origem.exists() )
    	{
    		try {
    			FileUtils.copyDirectory( origem, destino );
    		}
        	catch ( IOException e )
        	{
    			LOG.error( e );
    		}
        	deleta( origem );
    	}
    }
    
    public void filtrarScreenshotsDeFalhaAPartirDosSucessos( String nomeSubpastaEvidencias )
    {
    	String pastaEvidencias = ProjectProperties.getInstance().getPastaEvidencias();
    	File pastaSuccess = new File( pastaEvidencias + File.separator + nomeSubpastaEvidencias
    			+ File.separator + "success" );
    	File[] listaDeSubpastasDeSuccess = pastaSuccess.listFiles();
    	for( File subpasta : listaDeSubpastasDeSuccess )
    	{
    		String nomeDaSubpasta = subpasta.getName();
    		System.out.println( nomeDaSubpasta );
    		File origem = new File( pastaEvidencias + File.separator + nomeSubpastaEvidencias
        			+ File.separator + "failure" + File.separator + nomeDaSubpasta );
    		File destino = new File( pastaEvidencias + File.separator + nomeSubpastaEvidencias
        			+ File.separator + "reprocessed" + File.separator + nomeDaSubpasta );
    		if ( origem.exists() )
        	{
        		try {
        			FileUtils.copyDirectory( origem, destino );
        		}
            	catch ( IOException e )
            	{
        			LOG.error( e );
        		}
            	deleta( origem );
        	}
    	}
    }
    
    private void deleta( File file )
    {
    	if ( file.isDirectory() )
    	{
    		File[] files = file.listFiles();
    		for ( int i = 0; i < files.length; i++ )
    		{
    			deleta( files[i] );
    		}
    	}
    	file.delete();
    }

    private boolean createFile(File screenshot) {
        boolean fileCreated = false;

        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                try {
                    fileCreated = screenshot.createNewFile();
                } catch (IOException errorCreatingScreenshot) {
                	LOG.error("Erro de criação de captura de tela.", errorCreatingScreenshot);
                }
            }
        }

        return fileCreated;
    }

    private void writeScreenshotToFile(WebDriver driver, File screenshot) {
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            screenshotStream.close();
        } catch (IOException unableToWriteScreenshot) {
        	LOG.error("Não foi possível salvar a captura de tela " + screenshot.getAbsolutePath(), unableToWriteScreenshot);
        }
    }
    
    private void saveScreenshot(String testName) {
    	try {
            WebDriver driver = LocalDriverManager.getDriver();
            if (driver != null) {
            	String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();
                String screenshotAbsolutePath = screenshotDirectory + File.separator + testName + ".png";
                File screenshot = new File(screenshotAbsolutePath);
                if (createFile(screenshot)) {
                    try {
                        writeScreenshotToFile(driver, screenshot);
                    } catch (ClassCastException weNeedToAugmentOurDriverObject) {
                        writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
                    }
                    LOG.info("Captura de tela salva em " + screenshotAbsolutePath);
                } else {
                    LOG.error("Não foi possível criar " + screenshotAbsolutePath);
                }
            } else {
            	LOG.error("Não foi possível fazer captura de tela porque o driver está null...");
            }
            
        } catch (Exception ex) {
            LOG.error("Não foi possível fazer captura de tela...", ex);
        }
    }

	private void finalizaTesteFeito( ITestResult testResult, String id, String nome, boolean status )
	{
    	if ( LocalDriverManager.getDriver() != null )
    	{
    		LOG.info( "Salvando resultado em captura de tela ..." );
    		capturaTela( id + "_" + nome, status );
    		
    		escreverResultadoEmPlanilha( id, status );
    		
    		quitWebDriver();
    	}
    	else
    	{
    		LOG.error( "Não foi possível detectar nenhum Driver ao fim do teste ..." );
    	}
    }
    
    private void capturaTela(String nome, boolean status) {
    	LocalDateTime tempo = LocalDateTime.now();
    	String data = tempo.format(DateTimeFormatter.ofPattern("uuuu_MM_dd"));
    	String hora = tempo.format(DateTimeFormatter.ofPattern("HHmmss"));
    	saveScreenshot(data + File.separator + (status ? "success" : "failure") + File.separator + nome + File.separator + hora);
    }
    
    private void escreverResultadoEmPlanilha( String id, boolean status )
    {
    	if ( new ExecutionProperties().isVerificarResultadoAnterior() )
    	{
    		RelatorioUtils.escreverResultado( id, status );
    	}
    }
    
    private String idCompleto(ITestResult testResult) {
    	String id = testResult.getMethod().getDescription();
    	if ( id == null || id.isEmpty() ) {
    		id = "00000";
    	}
    	return id;
    }
    
    private String nomeDoCenario( ITestResult testResult )
    {
    	String simpleName = testResult.getTestClass().getRealClass().getSimpleName();
    	simpleName = simpleName.replaceAll( "^Teste", "" );
    	
    	return simpleName;
    }
    
    private void quitWebDriver()
    {
    	LOG.info( "Eliminando o Driver ao fim do teste ..." );
		WebDriver driver = LocalDriverManager.getDriver();
		if ( driver != null )
		{
			try {
				driver.quit();
				driver = null;
				Thread.sleep( 2_000 );
			}
			catch ( Exception e )
			{
				LOG.error( "Erro ao comandar driver.quit() ...", e );
			}
		}
    }
}
