package br.com.rkoyanagui.core.domain;

import br.com.rkoyanagui.core.config.GerenciadorDeContexto;
import br.com.rkoyanagui.core.utils.UtilidadesDePagina;

public class LogicBase
{
	protected final UtilidadesDePagina utils = new UtilidadesDePagina();
	
	protected final String contexto = String.format(
			"(%s) : ", GerenciadorDeContexto.getUsuario().getId() );
}
