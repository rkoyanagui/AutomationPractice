package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class OpcaoDeEnderecoPage extends PageBase
{
	private By chkUseDeliveryAddressAsBillingAddress = By.id("addressesAreEquals");
	private By edtComment = By.name("message");
	private By btnProceedToCheckout = By.xpath("//span[text()='Proceed to checkout']/parent::button");
	private By btnContinueShopping = By.xpath("//a[contains(.,'Continue Shopping')]");
	
	public By getEnderecoDeEntrega( String endereco )
	{
		return By.xpath( String.format( "//li[contains(text(),'%s')]", endereco ) );
	}
	
	public By getChkUseDeliveryAddressAsBillingAddress() {
		return chkUseDeliveryAddressAsBillingAddress;
	}
	public By getEdtComment() {
		return edtComment;
	}
	public By getBtnProceedToCheckout() {
		return btnProceedToCheckout;
	}
	public By getBtnContinueShopping() {
		return btnContinueShopping;
	}
}
