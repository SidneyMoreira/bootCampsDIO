package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class FazerLoginPage extends BasePage {

	@FindBy(xpath = "//*[@id='SubmitLogin']")
	protected WebElement botaoFazerLogin;
	
	public void preencheCampoPorId(String id_campo, String value) {

		driver.findElement(By.id(id_campo)).sendKeys(value);
	}
	
	public FazerLoginPage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}


	public void fazerLoginUsuario() {
		preencheCampoPorId("email", "everisbootcamp@qabeginner.com");
		preencheCampoPorId("passwd", "QA@everis213");
		botaoFazerLogin.click();
		log("Fazer o Login na conta");
	}

}