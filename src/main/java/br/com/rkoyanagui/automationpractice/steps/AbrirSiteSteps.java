package br.com.rkoyanagui.automationpractice.steps;

import br.com.rkoyanagui.automationpractice.logic.AbrirSiteLogic;
import br.com.rkoyanagui.core.domain.StepsBase;
import cucumber.api.java.pt.E;

public class AbrirSiteSteps extends StepsBase
{
	private AbrirSiteLogic abrirSite = new AbrirSiteLogic();
	
	@E("^que esteja aberta a página de entrada do site \"([^\"]*)\"$")
	public void que_esteja_aberta_a_pagina_de_entrada_do_site( String site )
	{
		abrirSite.abrirSite( site );
	}
}
