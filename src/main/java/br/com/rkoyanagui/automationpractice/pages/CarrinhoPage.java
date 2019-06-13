package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class CarrinhoPage extends PageBase
{
	// Summary
	private By btnProceedToCheckout = By.xpath("//span[text()='Proceed to checkout']/parent::a");
	private By btnContinueShopping = By.xpath("//a[contains(.,'Continue shopping')]");
	
	public By imgProdutoListado( int indice )
	{
		return By.xpath( String.format( "(//table/tbody/tr/td//img)[%d]", indice ) );
	}
	
	public By imgPrimeiroProdutoListado()
	{
		return imgProdutoListado( 1 );
	}

	public By getBtnProceedToCheckout() {
		return btnProceedToCheckout;
	}

	public By getBtnContinueShopping() {
		return btnContinueShopping;
	}
}
