package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class DetalhesDoProdutoPage extends PageBase
{
	private By btnAddToCart = By.xpath("//span[text()='Add to cart']/parent::button");

	// quadro de produto adicionado com sucesso
	private By btnContinueShopping = By.xpath("//*[@title='Continue shopping']");
	private By btnProceedToCheckout = By.xpath("//*[@title='Proceed to checkout']");

	public By getBtnAddToCart() {
		return btnAddToCart;
	}

	public By getBtnContinueShopping() {
		return btnContinueShopping;
	}

	public By getBtnProceedToCheckout() {
		return btnProceedToCheckout;
	}
}
