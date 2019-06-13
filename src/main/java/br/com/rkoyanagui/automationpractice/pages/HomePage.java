package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class HomePage extends PageBase
{
	public By getProdutoAVenda( int indice )
	{
		return By.xpath( String.format( "(//div[@class='product-container']//img)[%d]", indice ) );
	}
	
	public By getPrimeiroProdutoAVenda()
	{
		return getProdutoAVenda( 1 );
	}
	
	public By getProdutoAVenda( String titulo )
	{
		return By.xpath( String.format( "//div[@class='product-container']//img[@title='%s']", titulo ) );
	}
}
