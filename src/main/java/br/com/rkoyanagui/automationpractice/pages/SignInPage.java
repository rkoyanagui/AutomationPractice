package br.com.rkoyanagui.automationpractice.pages;

import org.openqa.selenium.By;

import br.com.rkoyanagui.core.domain.PageBase;

public class SignInPage extends PageBase
{
	// Create an account
	private By edtNewEmail = By.id("email_create");
	private By btnCreateAnAccount = By.xpath("//button[contains(., 'Create an account')]");
	// Already registered
	private By edtEmail = By.id("email");
	private By edtPassword = By.id("passwd");
	private By btnSignIn = By.xpath("//button[contains(., 'Sign in')]");
	
	public By getEdtNewEmail() {
		return edtNewEmail;
	}

	public By getBtnCreateAnAccount() {
		return btnCreateAnAccount;
	}

	public By getEdtEmail() {
		return edtEmail;
	}

	public By getEdtPassword() {
		return edtPassword;
	}

	public By getBtnSignIn() {
		return btnSignIn;
	}
}
