package br.com.rkoyanagui.automationpractice.logic;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rkoyanagui.automationpractice.pages.CarrinhoPage;
import br.com.rkoyanagui.automationpractice.pages.DetalhesDoProdutoPage;
import br.com.rkoyanagui.automationpractice.pages.HomePage;
import br.com.rkoyanagui.core.domain.LogicBase;

public class EscolherProdutoLogic extends LogicBase
{
	private static final Logger LOG = LogManager.getLogger(EscolherProdutoLogic.class);
	private DetalhesDoProdutoPage detalhesDoProduto = new DetalhesDoProdutoPage();
	private CarrinhoPage carrinho = new CarrinhoPage();
	
	public void escolherProduto()
	{
		LOG.info( contexto + "Escolhendo um produto qualquer na loja ..." );
		utils.clica( new HomePage().getPrimeiroProdutoAVenda() );
	}
	
	public void adicionarAoCarrinho()
	{
		LOG.info( contexto + "Adicionando o produto escolhido ao carrinho ..." );
		utils.espera( 5_000 );
		utils.entrarNoFrame( 3, 20 );
		utils.clica( detalhesDoProduto.getBtnAddToCart() );
		utils.esperaAteElementoSumir( detalhesDoProduto.getBtnAddToCart() );
	}
	
	public void prosseguirAoCheckout()
	{
		LOG.info( contexto + "Prosseguindo para o checkout ..." );
		utils.voltarParaConteudoPrincipal();
		utils.clica( detalhesDoProduto.getBtnProceedToCheckout() );
	}
	
	public void validarQueProdutoEstaNoCarrinho()
	{
		LOG.info( contexto + "Validando que o produto foi adicionado ao carrinho ..." );
		assertTrue( utils.estaVisivel( carrinho.imgPrimeiroProdutoListado() ) );
	}
}
