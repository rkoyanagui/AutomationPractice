package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class FimDaCompraPage extends PageBase
{
	private By txtYourOrderIsComplete = By.xpath("//*[contains(text(),'Your order') and contains(text(),'is complete')]");
	private By btnBackToOrders = By.xpath("//a[contains(text(),'Back to orders')]");
	
	public By getTxtYourOrderIsComplete() {
		return txtYourOrderIsComplete;
	}

	public By getBtnBackToOrders() {
		return btnBackToOrders;
	}
}
