package br.com.rkoyanagui.core.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.FailurePolicy;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import br.com.rkoyanagui.core.exceptions.FrameworkException;

public class TestNgSuiteRunner
{
	private static final Logger LOG = LogManager.getLogger(TestNgSuiteRunner.class);
	
	public void execute( String[] testClassNames )
	{
		execute( testClassNames, null, 1 );
	}
	
	public void execute( String[] testClassNames, String[] includedGroups )
	{
		execute( testClassNames, includedGroups, 1 );
	}
	
	public void execute( String[] testClassNames, int threadCount )
	{
		execute( testClassNames, null, threadCount );
	}
	
	public void execute( String[] testClassNames,
						 String[] includedGroups,
						 int threadCount )
	{
		LOG.info( "------------------------ Início da execução ------------------------" );
		
		XmlSuite suite = null;
		XmlRun runTag = null;
		XmlGroups groups = null;
		
		if ( includedGroups != null )
		{
			runTag = new XmlRun();
			for ( String group : includedGroups ) {
				runTag.onInclude( group );
			}
			groups = new XmlGroups();
			groups.setRun( runTag );
		}
		
		suite = setUpSuite( testClassNames, groups, threadCount );
		
		try {
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add( suite );
			TestNG tng = new TestNG();
			tng.setPreserveOrder( true );
			tng.setUseDefaultListeners( false );
			tng.setXmlSuites( suites );
			tng.run();
		} catch (Exception e) {
			String message = "Falha ao iniciar suite de teste ... ";
			LOG.error( message, e );
			throw new FrameworkException( message, e );
		}
	}
	
	private XmlSuite setUpSuite( String[] testClassNames,
								 XmlGroups groups,
								 int threadCount )
	{
		if ( threadCount <= 0 )
		{
			String message = "É necessário definir o número máximo de threads.";
			throw new FrameworkException( message );
		}
		
		ArrayList<XmlClass> classes = new ArrayList<XmlClass>();
		for ( String className : testClassNames )
		{
			classes.add( new XmlClass(className) );
		}
		
		XmlSuite suite = new XmlSuite();
		suite.setName("Suite");
		suite.setParallel( ParallelMode.CLASSES );
		suite.setConfigFailurePolicy( FailurePolicy.SKIP );
		
		suite.addListener(br.com.rkoyanagui.core.listeners
				.ScreenshotListener.class.getCanonicalName());
		
		XmlTest test = new XmlTest( suite );
		test.setName( "Test" );
		if ( groups != null )
		{
			test.setGroups( groups );
		}
		test.setXmlClasses( classes );
		
		LOG.info( "Número máximo de threads em paralelo na suíte: " + threadCount );
		suite.setThreadCount( threadCount );
		
		return suite;
	}
}
