package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class FinalizaPage extends BasePage {
	
	@FindBy(xpath = "//*[text()='I confirm my order']")
	protected WebElement botaoConfirmar;
	
	public FinalizaPage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}
	
	
	public boolean aMensagemEsperada(String mensagemEsperada) {
		waitElement(botaoConfirmar, 5).click();
		boolean aMensagemApresentadaFoiAEsperada = isElementDisplayed(By.xpath("//*[text()='" + mensagemEsperada + "']"));
		
		if(aMensagemApresentadaFoiAEsperada) {
			log("Apresentou a mensagem esperada.");
			return true;
		}
		logFail("Não apresentou a mensagem esperada.");
		return false;
	}

}