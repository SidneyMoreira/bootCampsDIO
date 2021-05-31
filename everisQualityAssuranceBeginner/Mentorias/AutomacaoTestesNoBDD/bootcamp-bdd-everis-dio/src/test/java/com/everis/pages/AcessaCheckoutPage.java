package com.everis.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class AcessaCheckoutPage extends BasePage {

	@FindBy(xpath = "//*[contains(@class, 'standard-checkout')][@title='Proceed to checkout']")
	protected WebElement botaoFazerCheckout;
	
	
	public AcessaCheckoutPage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}


	public void fazerCheckoutProduto() {
		botaoFazerCheckout.click();
		log("Fazer o checkout no carrinho");
	}
	

}