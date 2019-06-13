package br.com.rkoyanagui.automationpractice.steps;

import br.com.rkoyanagui.automationpractice.logic.CadastrarClienteLogic;
import br.com.rkoyanagui.automationpractice.logic.EscolherProdutoLogic;
import br.com.rkoyanagui.automationpractice.logic.FinalizarCompraLogic;
import br.com.rkoyanagui.core.domain.StepsBase;
import cucumber.api.java.pt.E;

public class PrimeiraCompraSteps extends StepsBase
{
	private final EscolherProdutoLogic escolherProduto = new EscolherProdutoLogic();
	private final CadastrarClienteLogic cadastrarCliente = new CadastrarClienteLogic();
	private final FinalizarCompraLogic finalizarCompra = new FinalizarCompraLogic();
	
	@E("^escolho um produto qualquer na loja$")
	public void escolho_um_produto_qualquer_na_loja()
	{
		escolherProduto.escolherProduto();
	}
	
	@E("^adiciono o produto escolhido ao carrinho$")
	public void adiciono_o_produto_escolhido_ao_carrinho()
	{
		escolherProduto.adicionarAoCarrinho();
	}
	
	@E("^prossigo para o checkout$")
	public void prossigo_para_o_checkout()
	{
		escolherProduto.prosseguirAoCheckout();
	}
	
	@E("^valido que o produto foi adicionado ao carrinho$")
	public void valido_que_o_produto_foi_adicionado_ao_carrinho()
	{
		escolherProduto.validarQueProdutoEstaNoCarrinho();
	}
	
	@E("^realizo meu cadastro preenchendo todos os campos obrigatórios dos formulários$")
	public void realizo_meu_cadastro()
	{
		cadastrarCliente.prosseguirParaSignIn();
		cadastrarCliente.cadastrarNovoEmail();
		cadastrarCliente.preencherDadosPessoais();
	}
	
	@E("^valido que o endereço está correto$")
	public void valido_o_endereco()
	{
		finalizarCompra.validarEndereco();
	}
	
	@E("^aceito os termos de serviço$")
	public void aceito_os_termos_de_servico()
	{
		finalizarCompra.aceitarTermosDeServico();
	}
	
	@E("^valido o valor da compra$")
	public void valido_o_valor_da_compra()
	{
		finalizarCompra.validarValorDaCompra();
	}
	
	@E("^seleciono um método de pagamento$")
	public void seleciono_um_metodo_de_pagamento()
	{
		finalizarCompra.selecionarMetodoDePagamento();
	}
	
	@E("^confirmo a compra$")
	public void confirmo_a_compra()
	{
		finalizarCompra.confirmarCompra();
	}
	
	@E("^o site deve finalizar a compra com sucesso$")
	public void o_site_deve_finalizar_a_compra_com_sucesso()
	{
		finalizarCompra.validarFimDaCompra();
	}
}
