package br.com.rkoyanagui.automationpractice.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rkoyanagui.automationpractice.pages.BannerPage;
import br.com.rkoyanagui.core.domain.LogicBase;

public class AbrirSiteLogic extends LogicBase
{
	private static final Logger LOG = LogManager.getLogger(AbrirSiteLogic.class);
	private BannerPage banner = new BannerPage();
	
	public void abrirSite( String site )
	{
		LOG.info( contexto + String.format( "Abrindo site '%s' ...", site ) );
		utils.maximiza();
		utils.navega( site );
		utils.esperaVisibilidadeDe( banner.getImgBannerTopo() );
	}
}
