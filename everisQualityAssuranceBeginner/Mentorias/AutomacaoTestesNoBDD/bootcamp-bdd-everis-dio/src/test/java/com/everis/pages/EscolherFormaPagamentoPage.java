package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class EscolherFormaPagamentoPage extends BasePage {
	
	public EscolherFormaPagamentoPage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}


	public void escolherformaPagmento(String formaPagamento) {
		WebElement formaPagamentoTela = driver.findElement(By.xpath("//*[contains(text(), '" + formaPagamento + "')] | //*[text()= '" + formaPagamento + "']"));
		moveToElement(formaPagamentoTela);
		formaPagamentoTela.click();
		log("A forma de pagamento escolhido foi " + formaPagamentoTela);
	}

}