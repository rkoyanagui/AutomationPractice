package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class CadastroDeClientePage extends PageBase
{
	private By txtPageTitle = By.xpath("//*[text()='Create an account']");
	
	// INFORMACOES PESSOAIS
	// obrigatorios
	private By edtCustomerFirstName = By.id("customer_firstname");
	private By edtCustomerLastName = By.id("customer_lastname");
	private By edtEmail = By.id("email");
	private By edtPassword = By.id("passwd");
	// opcionais
	private By slcBirthDay = By.id("days");
	private By slcBirthMonth = By.id("months");
	private By slcBirthYear = By.id("years");
	private By chkNewsletter = By.id("newsletter");
	private By chkReceiveSpecialOffers = By.id("optin");
	
	// ENDERECO
	// obrigatorios
	private By edtFirstName = By.id("firstname");
	private By edtLastName = By.id("lastname");
	private By edtAddress = By.id("address1");
	private By edtCity = By.id("city");
	private By slcState = By.id("id_state");
	private By edtPostalCode = By.id("postcode");
	private By slcCountry = By.id("id_country");
	private By edtMobilePhone = By.id("phone_mobile");
	private By edtAddressAlias = By.id("alias");
	// opcionais
	private By edtCompany = By.id("company");
	private By edtAddressContinuacao = By.id("address2");
	private By edtAdditionalInfo = By.id("other");
	private By edtHomePhone = By.id("phone");
	
	private By btnRegister = By.xpath("//span[text()='Register']/parent::button");
	
	public enum Sexo
	{
		MASCULINO( 1 ),
		FEMININO( 2 );
		
		private final int indice;
		
		private Sexo( int indice )
		{
			this.indice = indice;
		}
		
		public static Sexo achaPorIndice( int indiceProcurado )
		{
			Sexo elementoProcurado = null;
			Sexo[] todos = Sexo.values();
			for (Sexo elemento : todos)
			{
				if ( elemento.indice == indiceProcurado )
				{
					elementoProcurado = elemento;
					break;
				}
			}
			return elementoProcurado;
		}
		
		public String getIndice()
		{
			return String.valueOf( indice );
		}
	}
	
	public enum Estado
	{
		// de 1 a 53, sem pular nenhum numero
		ALABAMA	(1),
		ALASKA	(2),
		ARIZONA	(3),
		ARKANSAS	(4),
		CALIFORNIA	(5),
		COLORADO	(6),
		CONNECTICUT	(7),
		DELAWARE	(8),
		DISTRICT_OF_COLUMBIA	(53),
		FLORIDA	(9),
		GEORGIA	(10),
		HAWAII	(11),
		IDAHO	(12),
		ILLINOIS	(13),
		INDIANA	(14),
		IOWA	(15),
		KANSAS	(16),
		KENTUCKY	(17),
		LOUISIANA	(18),
		MAINE	(19),
		MARYLAND	(20),
		MASSACHUSETTS	(21),
		MICHIGAN	(22),
		MINNESOTA	(23),
		MISSISSIPPI	(24),
		MISSOURI	(25),
		MONTANA	(26),
		NEBRASKA	(27),
		NEVADA	(28),
		NEW_HAMPSHIRE	(29),
		NEW_JERSEY	(30),
		NEW_MEXICO	(31),
		NEW_YORK	(32),
		NORTH_CAROLINA	(33),
		NORTH_DAKOTA	(34),
		OHIO	(35),
		OKLAHOMA	(36),
		OREGON	(37),
		PENNSYLVANIA	(38),
		PUERTO_RICO	(51),
		RHODE_ISLAND	(39),
		SOUTH_CAROLINA	(40),
		SOUTH_DAKOTA	(41),
		TENNESSEE	(42),
		TEXAS	(43),
		US_VIRGIN_ISLANDS	(52),
		UTAH	(44),
		VERMONT	(45),
		VIRGINIA	(46),
		WASHINGTON	(47),
		WEST_VIRGINIA	(48),
		WISCONSIN	(49),
		WYOMING	(50);
		
		private final int indice;
		
		private Estado( int indice )
		{
			this.indice = indice;
		}
		
		public static Estado achaPorIndice( int indiceProcurado )
		{
			Estado elementoProcurado = null;
			Estado[] todos = Estado.values();
			for (Estado elemento : todos)
			{
				if ( elemento.indice == indiceProcurado )
				{
					elementoProcurado = elemento;
					break;
				}
			}
			return elementoProcurado;
		}
		
		public String getIndice()
		{
			return String.valueOf( indice );
		}
	}
	
	public enum Pais
	{
		UNITED_STATES( 21 );
		
		private final int indice;
		
		private Pais( int indice )
		{
			this.indice = indice;
		}
		
		public static Pais achaPorIndice( int indiceProcurado )
		{
			Pais elementoProcurado = null;
			Pais[] todos = Pais.values();
			for (Pais elemento : todos)
			{
				if ( elemento.indice == indiceProcurado )
				{
					elementoProcurado = elemento;
					break;
				}
			}
			return elementoProcurado;
		}
		
		public String getIndice()
		{
			return String.valueOf( indice );
		}
	}
	
	public By rbtnSexo( Sexo sexo )
	{
		return By.xpath( String.format( "//input[@type='radio' and @value='%d']", sexo.getIndice() ) );
	}

	public By getTxtPageTitle() {
		return txtPageTitle;
	}

	public By getEdtCustomerFirstName() {
		return edtCustomerFirstName;
	}

	public By getEdtCustomerLastName() {
		return edtCustomerLastName;
	}

	public By getEdtEmail() {
		return edtEmail;
	}

	public By getEdtPassword() {
		return edtPassword;
	}

	public By getSlcBirthDay() {
		return slcBirthDay;
	}

	public By getSlcBirthMonth() {
		return slcBirthMonth;
	}

	public By getSlcBirthYear() {
		return slcBirthYear;
	}

	public By getChkNewsletter() {
		return chkNewsletter;
	}

	public By getChkReceiveSpecialOffers() {
		return chkReceiveSpecialOffers;
	}

	public By getEdtFirstName() {
		return edtFirstName;
	}

	public By getEdtLastName() {
		return edtLastName;
	}

	public By getEdtAddress() {
		return edtAddress;
	}

	public By getEdtCity() {
		return edtCity;
	}

	public By getSlcState() {
		return slcState;
	}

	public By getEdtPostalCode() {
		return edtPostalCode;
	}

	public By getSlcCountry() {
		return slcCountry;
	}

	public By getEdtMobilePhone() {
		return edtMobilePhone;
	}

	public By getEdtAddressAlias() {
		return edtAddressAlias;
	}

	public By getEdtCompany() {
		return edtCompany;
	}

	public By getEdtAddressContinuacao() {
		return edtAddressContinuacao;
	}

	public By getEdtAdditionalInfo() {
		return edtAdditionalInfo;
	}

	public By getEdtHomePhone() {
		return edtHomePhone;
	}

	public By getBtnRegister() {
		return btnRegister;
	}
}
