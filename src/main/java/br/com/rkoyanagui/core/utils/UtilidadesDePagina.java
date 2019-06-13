package br.com.rkoyanagui.core.utils;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.rkoyanagui.core.config.ExecutionProperties;
import br.com.rkoyanagui.core.config.LocalDriverManager;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class UtilidadesDePagina {
	private static final Logger LOG = LogManager.getLogger(UtilidadesDePagina.class);

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Duration timeoutDuration;
	protected Duration d300millis = Duration.ofMillis(300);
	private ExecutionProperties execProperties = new ExecutionProperties();
	protected static int TIMEOUT;
	protected static int PAGELOAD_TIMEOUT;

	public UtilidadesDePagina() {
		TIMEOUT = execProperties.getImplicitTimeOut();
		PAGELOAD_TIMEOUT = execProperties.getWaitPageLoad();
		timeoutDuration = Duration.ofSeconds( TIMEOUT );
		driver = LocalDriverManager.getDriver();
		driver.manage().timeouts().implicitlyWait( TIMEOUT, TimeUnit.SECONDS );
		driver.manage().timeouts().pageLoadTimeout( PAGELOAD_TIMEOUT, TimeUnit.SECONDS );
		wait = new WebDriverWait(driver, TIMEOUT);
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * Configura pageLoadTimeout para 10 segundos, e implicitlyWait para 100
	 * milisegundos.
	 */
	public void baixaTimeouts() {
		// driver.manage().timeouts().pageLoadTimeout( 10, TimeUnit.SECONDS );
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
	}

	/**
	 * Configura pageLoadTimeout para 40 segundos, e implicitlyWait para 20
	 * segundos.
	 */
	public void sobeTimeouts() {
		// driver.manage().timeouts().pageLoadTimeout( 40, TimeUnit.SECONDS );
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void maximiza()
	{
		try {
			driver.manage().window().maximize();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void navega( String endereco )
	{
		try {
			driver.get( endereco );
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void entrarNoElementoAtivo()
	{
		try {
			driver.switchTo().activeElement();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void entrarNoFrame( int indice )
	{
		try {
			driver.switchTo().frame( indice );
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void entrarNoFrame( int indice, int tentativas )
	{
		int i = 0;
		boolean sucesso = false;
		
		try {
			while ( !sucesso && i < tentativas )
			{
				try {
					espera( 1_000 );
					driver.switchTo().frame( indice );
					sucesso = true;
				}
				catch ( NoSuchFrameException e )
				{
					if ( i + 1 >= tentativas )
					{
						throw new WebDriverException(e);
					}
				}
				i++;
			}
		}
		catch ( WebDriverException e )
		{
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void voltarParaConteudoPrincipal()
	{
		try {
			driver.switchTo().defaultContent();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public String obtemCodigoFonteDaPagina() {
		try {
			return driver.getPageSource();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public String leAtualUrl() {
		try {
			return driver.getCurrentUrl();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void switchToDefaultContent() {
		try {
			TargetLocator locator = driver.switchTo();
			locator.defaultContent();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	// ********************* Título ************************

	/**
	 * Para usar em conjunto com 'WebDriverWait'. Procura no início do título da
	 * página.
	 * 
	 * @param frase
	 *            que será pesquisada
	 * @return verdadeiro se e quando a pesquisa for bem-sucedida.
	 */
	public ExpectedCondition<Boolean> tituloDaPaginaComecaCom(final String frase) {
		try {
			return driver -> driver.getTitle().toLowerCase().startsWith(frase.toLowerCase());
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	/**
	 * Para usar em conjunto com 'WebDriverWait'. Procura no final do título da
	 * página.
	 * 
	 * @param frase
	 *            que será pesquisada
	 * @return verdadeiro se e quando a pesquisa for bem-sucedida.
	 */
	public ExpectedCondition<Boolean> tituloDaPaginaTerminaCom(final String frase) {
		try {
			return driver -> driver.getTitle().toLowerCase().endsWith(frase.toLowerCase());
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	/**
	 * Para usar em conjunto com 'WebDriverWait'. Procura ao longo do título da
	 * página.
	 * 
	 * @param frase
	 *            que será pesquisada
	 * @return verdadeiro se e quando a pesquisa for bem-sucedida.
	 */
	public ExpectedCondition<Boolean> tituloDaPaginaContem(final String frase) {
		try {
			return driver -> driver.getTitle().toLowerCase().contains(frase.toLowerCase());
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public boolean estaVisivel(By by)
	{
		return estaVisivel( by, TIMEOUT );
	}

	public boolean estaVisivel(By by, int miliSeg) {
		
		for (int i = 1; i <= miliSeg; i++) {
			System.out.println(i + " Segundo(s)");
			try {
				if (driver.findElement(by).isDisplayed()) {
					LOG.info(">>> Elemento Visivel: " + by.toString());
					return true;
				}
			} catch (Exception e) {
				espera(1000);
			}
		}
		LOG.info(">>> Elemento Nao Visivel: " + by.toString() + " | Tempo Utilizado: " + miliSeg);
		return false;
	}
	
	public boolean estaVisivel(WebElement element)
	{
		return estaVisivel( element, TIMEOUT );
	}
	
	public boolean estaVisivel(WebElement element, int miliSeg) {
		
		for (int i = 1; i <= miliSeg; i++) {
			System.out.println(i + " Segundo(s)");
			try {
				if (element.isDisplayed()) {
					LOG.info(">>> Elemento Visivel: " + element.toString());
					return true;
				}
			} catch (Exception e) {
				espera(1000);
			}
		}
		LOG.info(">>> Elemento Nao Visivel: " + element.toString() + " | Tempo Utilizado: " + miliSeg);
		return false;

	}

//	public boolean estaVisivel(By element) {
//		boolean isDisplayed = true;
//
//		try {
//			driver.manage().timeouts().implicitlyWait(d60seconds.getSeconds(), TimeUnit.SECONDS);
//			driver.findElement(element).isDisplayed();
//		} catch (WebDriverException e) {
//			LOG.error(e);
//			isDisplayed = false;
//		} finally {
//			this.sobeTimeouts();
//		}
//
//		return isDisplayed;
//	}
//
//	public boolean estaVisivel(By element, int milliseconds) {
//		boolean isDisplayed = true;
//
//		try {
//			driver.manage().timeouts().implicitlyWait(milliseconds, TimeUnit.MILLISECONDS);
//			driver.findElement(element).isDisplayed();
//		} catch (WebDriverException e) {
//			LOG.error( e.getMessage() );
//			isDisplayed = false;
//		} finally {
//			this.sobeTimeouts();
//		}
//
//		return isDisplayed;
//	}

//	public void escondeTeclado() {
//		appiumTap( 1, 1 );
		/*int tentativas = 3;
		int i = 0;
		while ( i < tentativas ) {
			try {
				espera( 333 );
				if ( ( ( IOSDriver<IOSElement> ) driver ).isKeyboardShown() ) {
					( ( IOSDriver<IOSElement> ) driver ).hideKeyboard( HideKeyboardStrategy.TAP_OUTSIDE );
					espera( 3_000 );
					break;
				}
			} catch ( WebDriverException e ) {
				LOG.error( "Erro ao esconder o teclado: " + e.getMessage() );
			}
			i++;
		}*/
//	}

	public void clicaElementoContendo(By by, String texto) {
		try {
			this.buscaElementoContendo(by, texto).click();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public WebElement buscaElementoContendo(By by, String texto) {
		try {
			WebElement webElement = null;
			List<WebElement> elements = driver.findElements(by);
			for (WebElement element : elements) {
				if (element.isDisplayed() && element.getText().equals(texto)) {
					webElement = element;
					break;
				}
			}
			return webElement;
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public WebElement buscaElemento(By by) {
		try {
			return driver.findElement(by);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public List<WebElement> buscaElementos(By by) {
		try {
			return driver.findElements(by);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public void darTab(By by) {
		try {
			driver.findElement(by).sendKeys(Keys.TAB);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	// ********************* Seleção ************************

	public boolean estaMarcado(By by) {
		try {
			return driver.findElement(by).isSelected();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	// ********************* Textos e Atributos ************************

	public String le(By by) {
		try {
			esperaVisibilidadeDe( by );
			return driver.findElement(by).getText();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void limpaSimples(By by) {
		try {
			driver.findElement(by).clear();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public void limpa(By by) {
		esperaAteEstarClicavel(by);
		limpaSimples(by);
	}
	
	public void limpa(By by, int segundosAteTimeout) {
		esperaAteEstarClicavel(by, segundosAteTimeout);
		limpaSimples(by);
	}
	
	public void escreveSimples(By by, String texto) {
		try {
			driver.findElement(by).sendKeys(texto);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public void escreve(By by, String texto) {
		esperaAteEstarClicavel(by);
		limpaSimples(by);
		escreveSimples(by, texto);
	}
	
	public void escreve(By by, String texto, int segundosAteTimeout) {
		esperaAteEstarClicavel(by, segundosAteTimeout);
		limpaSimples(by);
		escreveSimples(by, texto);
	}

	public void escreveSemLimpar(By by, String texto) {
		esperaAteEstarClicavel(by);
		escreveSimples( by, texto );
	}
	
	public void escreveSemLimpar(By by, String texto, int segundosAteTimeout) {
		esperaAteEstarClicavel(by, segundosAteTimeout);
		escreveSimples( by, texto );
	}
	
	public void setValue( By by, String valor )
	{
		( ( MobileElement) buscaElemento( by ) ).setValue( valor );
	}

	public String obtemAtributo(By by, String atributo) {
		try {
			return driver.findElement(by).getAttribute(atributo);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	// ********************* JS ************************

	public Object executaJS(String cmd, Object... param) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript(cmd, param);
		} catch (Exception e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	// ********************* Clipboard ************************

	public String obtemTextoDoClipboard() {
		String text = new String();
		try {
			text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
		return text;
	}

	// ********************* Clicar ************************

	public void clicaSimples(By by) {
		try {
			driver.findElement(by).click();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void clicaESegura(By by) {
		WebElement element = esperaAteEstarClicavel(by);
//		TouchActions action = new TouchActions(driver);
//		action.longPress(element);
//		action.perform();
		
		TouchAction action = new TouchAction( ( MobileDriver ) driver );
		Point point = encontraCentroDoElemento( element );
		action.longPress(( PointOption.point( point.getX(), point.getY() ) ) )
//		.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
		.perform();
		
//		action.longPress((LongPressOptions) element).release().perform();
	}

	public void clica(By by) {
		esperaAteEstarClicavel(by);
		clicaSimples(by);
	}

	public void clica(By by, int segundosAteTimeout) {
		esperaAteEstarClicavel(by, segundosAteTimeout);
		clicaSimples(by);
	}
	
	public void selecionaEmDropDown( By by, String value )
	{
		try {
			Select select = new Select( buscaElemento( by ) );
			select.selectByValue( value );
		}
		catch (WebDriverException e)
		{
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public void moveAoElemento(By by) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(by)).click().build().perform();
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public Point encontraCentroDoElemento( WebElement element )
	{
		try {
			Rectangle rect = element.getRect();
			int x = rect.getX() + ( rect.getWidth() / 2 );
			int y = rect.getY() + ( rect.getHeight() / 2 );
			LOG.info( "Centro do elemento X: " + x + " | Y: " + y );
			return new Point( x, y );
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public Point encontraCentroDoElemento( By by )
	{
		return encontraCentroDoElemento( buscaElemento( by ) );
	}
	
	@SuppressWarnings("rawtypes")
	public void appiumTap(int x, int y) {
		try {
			TouchAction action = new TouchAction((MobileDriver) driver);

			action.tap( PointOption.point( x, y ) )
//			.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
			.perform();

			espera(250);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public void appiumTap( Point point )
	{
		appiumTap( point.getX(), point.getY() );
	}

	// ********************* Scroll ************************
	
	/**
	 * 
	 * Scroll para descer a pagina
	 *  
	 */
	
	public void scrollParaDescer(){
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
		
	}
	
	/**
	 * 
	 * Scroll para subir a pagina
	 *  
	 */
	
	public void scrollParaSubir(){
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile: scroll", scrollObject);
		
	}
	
	/**
	 * 
	 * Scroll para ir para a direita a pagina
	 *  
	 */
	
	public void scrollParaDireita(){
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "right");
		js.executeScript("mobile: scroll", scrollObject);
		
	}
	
	/**
	 * 
	 * Scroll para ir para a esquerda a pagina
	 *  
	 */
	
	public void scrollParaEsquerda(){
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "left");
		js.executeScript("mobile: scroll", scrollObject);
		
	}

	/**
	 * O mesmo que {@link appiumDeslizaAcimaEProcura}, com 5 tentativas por padrão.
	 * 
	 */
	public WebElement appiumDeslizaAcimaEProcura(By by) {
		return appiumDeslizaAcimaEProcura(by, 5);
	}

	/**
	 * O mesmo que {@link appiumDeslizaAbaixoEProcura} só que para cima ao invés de
	 * para baixo.
	 * 
	 */
	public WebElement appiumDeslizaAcimaEProcura(By by, int tentativas) {
		try {
			Dimension dimension = driver.manage().window().getSize();
//			LOG.info("Dimensões da tela: " + dimension);

			Double screenHeightStart = dimension.getHeight() * 0.2;
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimension.getHeight() * 0.5;
			int scrollEnd = screenHeightEnd.intValue();

			int width = new Double(dimension.getWidth() * 0.5).intValue();

			return appiumDeslizaEProcura(width, scrollStart, width, scrollEnd, by, tentativas);
		} catch (WebDriverException e) {
			LOG.error(e.getMessage());
			throw new WebDriverException(e);
		}
	}

	/**
	 * O mesmo que {@link appiumDeslizaAbaixoEProcura}, com 5 tentativas por padrão.
	 * 
	 */
	public WebElement appiumDeslizaAbaixoEProcura(By by) {
		return appiumDeslizaAbaixoEProcura(by, 5);
	}
	
	public WebElement appiumDeslizaAbaixoEProcura(By by, Double larguraTela) {
		return appiumDeslizaAbaixoEProcura(by, larguraTela, 5);
	}

	/**
	 * Combina {@link appiumDeslizaAbaixo} com {@link appiumDeslizaEProcura}.
	 * 
	 */
	public WebElement appiumDeslizaAbaixoEProcura(By by, int tentativas) {
		try {
			Dimension dimension = driver.manage().window().getSize();
//			LOG.info("Dimensões da tela: " + dimension);

			Double screenHeightStart = dimension.getHeight() * 0.5;
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimension.getHeight() * 0.2;
			int scrollEnd = screenHeightEnd.intValue();

			int width = new Double(dimension.getWidth() * 0.5).intValue();
			
			return appiumDeslizaEProcura(width, scrollStart, width, scrollEnd, by, tentativas);
		} catch (WebDriverException e) {
			LOG.error(e.getMessage());
			throw new WebDriverException(e);
		}
	}
	
	public WebElement appiumDeslizaAbaixoEProcura(By by, double larguraTela, int tentativas) {
		try {
			Dimension dimension = driver.manage().window().getSize();
//			LOG.info("Dimensões da tela: " + dimension);

			Double screenHeightStart = dimension.getHeight() * 0.5;
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimension.getHeight() * 0.2;
			int scrollEnd = screenHeightEnd.intValue();

			int width = new Double(dimension.getWidth() * larguraTela).intValue();
			
			return appiumDeslizaEProcura(width, scrollStart, width, scrollEnd, by, tentativas);
		} catch (WebDriverException e) {
			LOG.error(e.getMessage());
			throw new WebDriverException(e);
		}
	}

	/**
	 * O mesmo que {@link appiumDeslizaEProcura}, com 5 tentativas por padrão.
	 * 
	 */
	public WebElement appiumDeslizaEProcura(int x1, int y1, int x2, int y2, By by) {
		return appiumDeslizaEProcura(x1, y1, x2, y2, by, 5);
	}

	/**
	 * Usando TouchAction do Appium, desliza do ponto (x1, y1) para o ponto (x2,
	 * y2). Antes de cada deslize, busca o elemento apontado por 'By'. Tenta
//	 * deslizar e buscar um certo número de 'tentativas'.
	 * 
	 * @param x1
	 *            primeiro ponto: coordenada horizontal
	 * @param y1
	 *            primeiro ponto: coordenada vertical
	 * @param x2
	 *            segundo ponto: coordenada horizontal
	 * @param y2
	 *            segundo ponto: coordenada vertical
	 * @param by
	 *            localizador do elemento procurado
	 * @param tentativas
	 *            quantas vezes deslizar e buscar o elemento
	 * @return O elemento procurado, ou nulo se não for encontrado.
	 */
	public WebElement appiumDeslizaEProcura(int x1, int y1, int x2, int y2, By by, int tentativas) {
		WebElement element = null;
		int contador = 0;

		do {
			try {
				element = driver.findElement(by);
			} catch (WebDriverException e) {
			}

			if (element == null) {
				appiumDesliza(x1, y1, x2, y2);
			}

			contador++;
		} while (element == null && contador < tentativas);

		return element;
	}

	/**
	 * Usando TouchAction do Appium, desliza de cima para baixo, traçando uma linha
	 * justaposta à lateral esquerda da tela, a distância de 30% da altura da tela.
	 * 
	 */
	public void appiumDeslizaAbaixo()
	{
		appiumDeslizaAbaixo( 1 );
	}
	
	/**
	 * Usando TouchAction do Appium, desliza de cima para baixo, traçando uma linha
	 * justaposta à lateral esquerda da tela, a distância de 30% da altura da tela.
	 * 
	 * @param qtasVezes quantas vezes deslizar
	 */
	public void appiumDeslizaAbaixo( int qtasVezes ) {
		try {
			Dimension dimension = driver.manage().window().getSize();
//			LOG.info("Dimensões da tela: " + dimension);

			Double screenHeightStart = dimension.getHeight() * 0.5;
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimension.getHeight() * 0.2;
			int scrollEnd = screenHeightEnd.intValue();

			if ( qtasVezes <= 0 )
			{
				qtasVezes = 1;
			}
			
			for ( int i = 0; i < qtasVezes; i++ )
			{
				appiumDesliza(0, scrollStart, 0, scrollEnd);
			}
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	/**
	 * Usando TouchAction do Appium, desliza do ponto (x1, y1) para o ponto (x2,
	 * y2).
	 * 
	 * @param x1
	 *            primeiro ponto: coordenada horizontal
	 * @param y1
	 *            primeiro ponto: coordenada vertical
	 * @param x2
	 *            segundo ponto: coordenada horizontal
	 * @param y2
	 *            segundo ponto: coordenada vertical
	 */
	public void appiumDeslizaLento( Point p1, Point p2 )
	{
		int x1 = p1.getX();
		int y1 = p1.getY();
		int x2 = p2.getX();
		int y2 = p2.getY();
		
		appiumDeslizaLento( x1, y1, x2, y2 );
	}
	
	/**
	 * Usando TouchAction do Appium, desliza do ponto (x1, y1) para o ponto (x2,
	 * y2).
	 * 
	 * @param x1
	 *            primeiro ponto: coordenada horizontal
	 * @param y1
	 *            primeiro ponto: coordenada vertical
	 * @param x2
	 *            segundo ponto: coordenada horizontal
	 * @param y2
	 *            segundo ponto: coordenada vertical
	 */
	@SuppressWarnings("rawtypes")
	public void appiumDeslizaLento(int x1, int y1, int x2, int y2) {
		try {
			TouchAction action = new TouchAction( ( MobileDriver ) driver );

			action.longPress( PointOption.point( x1, y1 ) )
			.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
			.moveTo( PointOption.point( x2, y2 ) )
			.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
			.release()
			.perform();

			espera(250);
		}
		catch ( WebDriverException e )
		{
			LOG.error( e.getMessage() );
			throw new WebDriverException( e );
		}
	}
	
	/**
	 * Usando TouchAction do Appium, desliza do ponto (x1, y1) para o ponto (x2,
	 * y2).
	 * 
	 * @param x1
	 *            primeiro ponto: coordenada horizontal
	 * @param y1
	 *            primeiro ponto: coordenada vertical
	 * @param x2
	 *            segundo ponto: coordenada horizontal
	 * @param y2
	 *            segundo ponto: coordenada vertical
	 */
	public void appiumDesliza( Point p1, Point p2 )
	{
		int x1 = p1.getX();
		int y1 = p1.getY();
		int x2 = p2.getX();
		int y2 = p2.getY();
		
		appiumDesliza( x1, y1, x2, y2 );
	}

	/**
	 * Usando TouchAction do Appium, desliza do ponto (x1, y1) para o ponto (x2,
	 * y2).
	 * 
	 * @param x1
	 *            primeiro ponto: coordenada horizontal
	 * @param y1
	 *            primeiro ponto: coordenada vertical
	 * @param x2
	 *            segundo ponto: coordenada horizontal
	 * @param y2
	 *            segundo ponto: coordenada vertical
	 */
	@SuppressWarnings("rawtypes")
	public void appiumDesliza(int x1, int y1, int x2, int y2) {
		try {
			TouchAction action = new TouchAction( ( MobileDriver ) driver );

			action.press( PointOption.point( x1, y1 ) )
			.waitAction( WaitOptions.waitOptions( Duration.ofMillis( 3_000 ) ) )
			.moveTo( PointOption.point( x2, y2 ) )
			.release()
			.perform();

			espera(250);
		}
		catch ( WebDriverException e )
		{
			LOG.error( e.getMessage() );
			throw new WebDriverException( e );
		}
	}

	public void jsScrollToElement(By by) {
		try {
			WebElement element = driver.findElement(by);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	// ********************* Espera ************************

	public void espera(final int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			LOG.error("Erro em Thread.sleep( " + milliseconds + " ) : " + e.getMessage());
		}
	}

	public boolean esperaAteEstarMarcado(By by) {
		try {
			wait.until(ExpectedConditions.elementSelectionStateToBe(by, true));
			return estaMarcado(by);
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public WebElement esperaAteEstarClicavel(By by) {
		try {
			return wait.withTimeout(timeoutDuration).pollingEvery(d300millis).ignoring(StaleElementReferenceException.class)
					.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(by));
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public WebElement esperaAteEstarClicavel(By by, int segundosAteTimeout) {
		try {
			return wait.withTimeout(Duration.ofSeconds(segundosAteTimeout)).pollingEvery(d300millis)
					.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(by));
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public WebElement esperaAteEstarClicavel(WebElement element) {
		try {
			return wait.withTimeout(timeoutDuration).pollingEvery(d300millis).ignoring(StaleElementReferenceException.class)
					.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(element));
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}
	
	public WebElement esperaVisibilidadeDe(By by) {
		try {
			return wait.withTimeout(timeoutDuration).pollingEvery(d300millis).ignoring(StaleElementReferenceException.class)
					.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public WebElement esperaVisibilidadeDe(By by, int segundosAteTimeout) {
		try {
			return wait.withTimeout(Duration.ofSeconds(segundosAteTimeout)).pollingEvery(d300millis)
					.ignoring(StaleElementReferenceException.class)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	public WebElement esperaVisibilidadeDe(WebElement element) {
		try {
			return wait.withTimeout(timeoutDuration).pollingEvery(d300millis)
					.ignoring(StaleElementReferenceException.class)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOf(element));
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
	}

	/**
	 * A execução do teste é interrompida por este método até que o elemento:
	 * <p>
	 * (1) não seja encontrado; ou
	 * </p>
	 * <p>
	 * (2) esteja com referência caduca; ou
	 * </p>
	 * <p>
	 * (3) não seja exibido em tela; ou
	 * </p>
	 * <p>
	 * (4) tenha largura e altura ambas iguais a zero; ou
	 * </p>
	 * <p>
	 * (5) o tempo limite de espera de 5 segundos seja atingido
	 * </p>
	 * 
	 * @param by
	 *            localizador do elemento
	 * @return true se o elemento não for encontrado ou se a referência ao elemento
	 *         caducar
	 */
	public boolean esperaAteElementoSumir(By by) {
		return esperaAteElementoSumir(by, TIMEOUT);
	}

	/**
	 * A execução do teste é interrompida por este método até que o elemento:
	 * <p>
	 * (1) não seja encontrado; ou
	 * </p>
	 * <p>
	 * (2) esteja com referência caduca; ou
	 * </p>
	 * <p>
	 * (3) não seja exibido em tela; ou
	 * </p>
	 * <p>
	 * (4) tenha largura e altura ambas iguais a zero; ou
	 * </p>
	 * <p>
	 * (5) o tempo limite de espera seja atingido
	 * </p>
	 * 
	 * @param by
	 *            localizador do elemento
	 * @param segundosAteTimeout
	 *            tempo limite de espera
	 * @return true se o elemento não for encontrado ou se a referência ao elemento
	 *         caducar
	 */
	public boolean esperaAteElementoSumir(By by, int segundosAteTimeout) {
		WebElement webelement = null;
		boolean sumiu = false;
		try {
			webelement = this.driver.findElement(by);
			sumiu = esperaAteElementoSumir(webelement, segundosAteTimeout);
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			sumiu = true;
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
		return sumiu;
	}

	public boolean esperaAteElementoSumir(WebElement webelement, int segundosAteTimeout) {
		boolean sumiu = false;
		int contadorSegundos = 0;
		try {
			while (contadorSegundos < segundosAteTimeout && !sumiu) {
				if (!webelement.isDisplayed()
						|| (webelement.getSize().getWidth() == 0 && webelement.getSize().getHeight() == 0)) {
					sumiu = true;
				}
				this.espera(1_000);
				contadorSegundos++;
			}
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			sumiu = true;
		} catch (WebDriverException e) {
			LOG.error(e);
			throw new WebDriverException(e);
		}
		return sumiu;
	}
	
	public boolean esperaAteElementoMobileSumir(By by) {
		return esperaAteElementoSumir(by, 5);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean esperaAteElementoMobileSumir( By by, int segundosAteTimeout )
	{
		MobileElement element = null;
		boolean sumiu = false;
		int contadorSegundos = 0;
		try {
			while ( contadorSegundos < segundosAteTimeout && !sumiu )
			{
				element = ( MobileElement ) ( ( MobileDriver ) driver ).findElement( by );
				if ( element == null ) {
					sumiu = true;
				}
				this.espera(1_000);
				System.out.println(contadorSegundos + " Segundo(s)");
				contadorSegundos++;
			}
		} catch (WebDriverException e) {
			sumiu = true;
		} catch (Exception e) {
			sumiu = true;
			LOG.error(e);
		}
		return sumiu;
	}
}
