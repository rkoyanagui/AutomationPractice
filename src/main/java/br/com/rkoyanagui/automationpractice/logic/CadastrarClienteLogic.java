package br.com.rkoyanagui.automationpractice.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rkoyanagui.automationpractice.pages.CadastroDeClientePage;
import br.com.rkoyanagui.automationpractice.pages.CadastroDeClientePage.Estado;
import br.com.rkoyanagui.automationpractice.pages.CadastroDeClientePage.Pais;
import br.com.rkoyanagui.automationpractice.pages.CarrinhoPage;
import br.com.rkoyanagui.automationpractice.pages.SignInPage;
import br.com.rkoyanagui.core.config.GerenciadorDeContexto;
import br.com.rkoyanagui.core.domain.LogicBase;
import br.com.rkoyanagui.core.domain.PessoaFisica;
import br.com.rkoyanagui.core.exceptions.DataSearchException;
import br.com.rkoyanagui.core.storage.LeitorDaPlanilhaCadastroDeCliente;
import br.com.rkoyanagui.core.utils.UtilidadesDeFormulario;

public class CadastrarClienteLogic extends LogicBase
{
	private static final Logger LOG = LogManager.getLogger(CadastrarClienteLogic.class);
	private UtilidadesDeFormulario utilsFormulario = new UtilidadesDeFormulario();
	private CarrinhoPage carrinho = new CarrinhoPage();
	private CadastroDeClientePage cadastro = new CadastroDeClientePage();
	
	public void prosseguirParaSignIn()
	{
		LOG.info( contexto + "Prosseguindo para Sign In ..." );
		utils.clica( carrinho.getBtnProceedToCheckout() );
	}
	
	public void cadastrarNovoEmail()
	{
		SignInPage signin = new SignInPage();
		String novoEmail = utilsFormulario.geraMinusculasAleatorias( 10 ) + "@teste.com.br";;
		LOG.info( contexto + String.format( "Cadastrando novo e-mail '%s' ...", novoEmail ) );
		GerenciadorDeContexto.setInfo( "email", novoEmail );
		utils.escreve( signin.getEdtNewEmail() , novoEmail );
		utils.clica( signin.getBtnCreateAnAccount() );
	}
	
	public void preencherDadosPessoais()
	{
		LOG.info( contexto + "Preenchendo dados pessoais ..." );
		String nome = utilsFormulario.geraNomeAleatorio( 4 );
		String sobrenome = utilsFormulario.geraNomeAleatorio( 6 );
		String email = (String) GerenciadorDeContexto.getInfo( "email" );
		String senha = utilsFormulario.geraMinusculasAleatorias( 8 )
				+ Integer.toString( utilsFormulario.geraInteiroAleatorio( 10, 100 ) );
		String endereco = Integer.toString( utilsFormulario.geraInteiroAleatorio( 10, 100 ) )
				+ " " + utilsFormulario.geraNomeAleatorio( 6 ) + " St";
		GerenciadorDeContexto.setInfo( "endereco", endereco );
		String cidade = utilsFormulario.geraNomeAleatorio( 6 );
		Estado estado = Estado.achaPorIndice( utilsFormulario.geraInteiroAleatorio( 1, 54 ) );
		String codigoPostal = Integer.toString( utilsFormulario.geraInteiroAleatorio( 10000, 100000 ) );
		Pais pais = Pais.UNITED_STATES;
		String celular = Integer.toString( utilsFormulario.geraInteiroAleatorio( 100, 1000 ) )
				+ "-" + Integer.toString( utilsFormulario.geraInteiroAleatorio( 100, 1000 ) )
				+ "-" + Integer.toString( utilsFormulario.geraInteiroAleatorio( 1000, 10000 ) );
		String referencia = codigoPostal;
		
		PessoaFisica pf = new PessoaFisica(email, senha, nome, sobrenome, endereco, cidade,
				estado.toString(), codigoPostal, pais.toString(), celular, referencia);
		LOG.info( contexto + pf );
		try {
			LeitorDaPlanilhaCadastroDeCliente.escreverDados( pf );
		}
		catch ( DataSearchException e )
		{
			LOG.error( e );
		}
		
		utils.escreve( cadastro.getEdtCustomerFirstName(), nome );
		utils.escreve( cadastro.getEdtCustomerLastName(), sobrenome );
		utils.escreve( cadastro.getEdtPassword(), senha );
//		utils.escreve( cadastro.getEdtFirstName(), nome );
//		utils.escreve( cadastro.getEdtLastName(), sobrenome );
		utils.escreve( cadastro.getEdtAddress(), endereco );
		utils.escreve( cadastro.getEdtCity(), cidade );
		utils.selecionaEmDropDown( cadastro.getSlcState(), estado.getIndice() );
		utils.escreve( cadastro.getEdtPostalCode(), codigoPostal );
		utils.selecionaEmDropDown( cadastro.getSlcCountry(), pais.getIndice() );
		utils.escreve( cadastro.getEdtMobilePhone(), celular );
		utils.escreve( cadastro.getEdtAddressAlias(), referencia );
		
		utils.clica( cadastro.getBtnRegister() );
	}
}
