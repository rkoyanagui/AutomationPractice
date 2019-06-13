package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class ConfirmacaoPage extends PageBase
{
	private By btnOtherPaymentMethods = By.xpath("//a[contains(.,'Other payment methods')]");
	private By btnConfirmOrder = By.xpath("//span[text()='I confirm my order']/parent::button");
	
	public By getBtnOtherPaymentMethods() {
		return btnOtherPaymentMethods;
	}
	public By getBtnConfirmOrder() {
		return btnConfirmOrder;
	}
}
