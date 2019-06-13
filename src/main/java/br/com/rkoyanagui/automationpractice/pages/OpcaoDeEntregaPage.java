package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class OpcaoDeEntregaPage extends PageBase
{
	private By chkTermsOfService = By.xpath("//label[contains(text(),'terms of service')]/parent::*//input[@type='checkbox']");
	private By btnContinueShopping = By.xpath("//a[contains(.,'Continue shopping')]");
	private By btnProceedToCheckout = By.xpath("//span[contains(text(),'Proceed to checkout')]/parent::button");
	
	public By rbtnDeliveryOption( int indice )
	{
		return By.xpath( String.format( "(//input[@class='delivery_option_radio'])[%d]", indice ) );
	}
	
	public By txtDeliveryOptionPrice( int indice )
	{
		return By.xpath( String.format( "(//div[@class='delivery_option_price'])[%d]", indice ) );
	}

	public By getChkTermsOfService() {
		return chkTermsOfService;
	}

	public By getBtnContinueShopping() {
		return btnContinueShopping;
	}

	public By getBtnProceedToCheckout() {
		return btnProceedToCheckout;
	}
}
