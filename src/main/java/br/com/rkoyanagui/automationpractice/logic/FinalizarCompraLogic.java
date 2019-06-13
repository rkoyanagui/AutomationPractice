package br.com.rkoyanagui.automationpractice.logic;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rkoyanagui.automationpractice.pages.ConfirmacaoPage;
import br.com.rkoyanagui.automationpractice.pages.FimDaCompraPage;
import br.com.rkoyanagui.automationpractice.pages.OpcaoDeEnderecoPage;
import br.com.rkoyanagui.automationpractice.pages.OpcaoDeEntregaPage;
import br.com.rkoyanagui.automationpractice.pages.OpcaoDePagamentoPage;
import br.com.rkoyanagui.automationpractice.pages.OpcaoDePagamentoPage.MetodoDePagamento;
import br.com.rkoyanagui.core.config.GerenciadorDeContexto;
import br.com.rkoyanagui.core.domain.LogicBase;

public class FinalizarCompraLogic extends LogicBase
{
	private static final Logger LOG = LogManager.getLogger(FinalizarCompraLogic.class);
	private final OpcaoDeEnderecoPage enderecoPage = new OpcaoDeEnderecoPage();
	private final OpcaoDeEntregaPage opcaoDeEntrega = new OpcaoDeEntregaPage();
	private final OpcaoDePagamentoPage opcaoDePgto = new OpcaoDePagamentoPage();
	private final ConfirmacaoPage confirmacao = new ConfirmacaoPage();
	private final FimDaCompraPage fimDaCompra = new FimDaCompraPage();
	
	public void validarEndereco()
	{
		String endereco = (String) GerenciadorDeContexto.getInfo( "endereco" );
		LOG.info( contexto + String.format( "Validando o endereço '%s' ...", endereco ) );
		assertTrue( utils.estaVisivel( enderecoPage.getEnderecoDeEntrega( endereco ) ) );
		utils.clica( enderecoPage.getBtnProceedToCheckout() );
	}
	
	public void aceitarTermosDeServico()
	{
		LOG.info( contexto + "Aceitando os termos de serviço ..." );
		utils.clicaSimples( opcaoDeEntrega.rbtnDeliveryOption( 1 ) );
		String precoDaEntrega = utils.le( opcaoDeEntrega.txtDeliveryOptionPrice( 1 ) );
		LOG.info( contexto + String.format( "O preço da entrega é de '%s' ...", precoDaEntrega ) );
		utils.clicaSimples( opcaoDeEntrega.getChkTermsOfService() );
		utils.clica( opcaoDeEntrega.getBtnProceedToCheckout() );
	}
	
	public void validarValorDaCompra()
	{
		LOG.info( contexto + "Validando o valor da compra ..." );
		double valorProduto = 0;
		double valorEntrega = 0;
		double valorImposto = 0;
		
		if ( utils.estaVisivel( opcaoDePgto.getTxtValorDoProduto() ) )
		{
			valorProduto = Double.parseDouble(
					utils.le( opcaoDePgto.getTxtValorDoProduto() )
					.trim().replaceAll( "\\$", "" ) );
		}
		
		if ( utils.estaVisivel( opcaoDePgto.getTxtValorDaEntrega(), 2 ) )
		{
			valorEntrega = Double.parseDouble(
					utils.le( opcaoDePgto.getTxtValorDaEntrega() )
					.trim().replaceAll( "\\$", "" ) );
		}
		
		if ( utils.estaVisivel( opcaoDePgto.getTxtValorDeImposto(), 2 ) )
		{
			valorImposto = Double.parseDouble(
					utils.le( opcaoDePgto.getTxtValorDeImposto() )
					.trim().replaceAll( "\\$", "" ) );
		}
		
		assertTrue( utils.estaVisivel( opcaoDePgto.getTxtPrecoTotal() ) );
		double precoTotal = Double.parseDouble(
				utils.le( opcaoDePgto.getTxtPrecoTotal() )
				.trim().replaceAll( "\\$", "" ) );
		
		LOG.info( contexto + String.format( "valorProduto: %f"
				+ " | valorEntrega: %f"
				+ " | valorImposto: %f"
				+ " | precoTotal: %f ...",
				valorProduto, valorEntrega, valorImposto, precoTotal ) );
		assertTrue( ( precoTotal == valorProduto + valorEntrega + valorImposto ) );
	}
	
	public void selecionarMetodoDePagamento()
	{
		LOG.info( contexto + "Selecionando um método de pagamento ..." );
		utils.clica( opcaoDePgto.getBtnMetodoDePagamento( MetodoDePagamento.TRANSFERENCIA ) );
	}
	
	public void confirmarCompra()
	{
		LOG.info( contexto + "Confirmando a compra ..." );
		utils.clica( confirmacao.getBtnConfirmOrder() );
	}
	
	public void validarFimDaCompra()
	{
		LOG.info( contexto + "Validando o fim da compra ..." );
		assertTrue( utils.estaVisivel( fimDaCompra.getTxtYourOrderIsComplete() ) );
	}
}
