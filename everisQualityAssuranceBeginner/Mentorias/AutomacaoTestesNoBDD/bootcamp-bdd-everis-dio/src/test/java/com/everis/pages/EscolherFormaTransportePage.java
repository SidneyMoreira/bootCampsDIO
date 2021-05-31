package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class EscolherFormaTransportePage extends BasePage {
	
	@FindBy(xpath = "//*[@name='processCarrier']")
	protected WebElement botaoConfirmarMeioTransporte;
	
	public EscolherFormaTransportePage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}


	public void escolherformaTransporte() {
		WebElement aceitaTermos = driver.findElement(By.id("cgv"));
		aceitaTermos.click();
		waitElement(botaoConfirmarMeioTransporte, 5).click();
		log("Confirmar meio de transporte");
	}

}