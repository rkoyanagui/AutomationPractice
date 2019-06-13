package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class OpcaoDePagamentoPage extends PageBase
{
	private By txtValorDoProduto = By.id("total_product");
	private By txtValorDaEntrega = By.id("total_shipping");
	private By txtValorDeImposto = By.id("total_tax");
	private By txtPrecoTotal = By.id("total_price");
	
	private By btnContinueShopping = By.xpath("//a[contains(.,'Continue shopping')]");
	
	public enum MetodoDePagamento
	{
		TRANSFERENCIA( "bankwire" ),
		CHEQUE( "cheque" );
		
		private final String identificador;
		
		private MetodoDePagamento( String identificador )
		{
			this.identificador = identificador;
		}
		
		public String getIdentificador()
		{
			return identificador;
		}
	}
	
	public By getBtnMetodoDePagamento( MetodoDePagamento metodo )
	{
		return By.className( metodo.getIdentificador() );
	}
	
	public By getTxtValorDoProduto() {
		return txtValorDoProduto;
	}
	public By getTxtValorDaEntrega() {
		return txtValorDaEntrega;
	}
	public By getTxtValorDeImposto() {
		return txtValorDeImposto;
	}
	public By getTxtPrecoTotal() {
		return txtPrecoTotal;
	}
	public By getBtnContinueShopping() {
		return btnContinueShopping;
	}
}
