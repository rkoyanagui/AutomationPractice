package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class BannerPage extends PageBase
{
	private By imgBannerTopo = By.xpath("//div[@class='banner']//img");

	public By getImgBannerTopo() {
		return imgBannerTopo;
	}
}
