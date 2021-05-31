package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.everis.util.Hooks;

public class ConfirmarEnderecoPage extends BasePage {
	
	@FindBy(xpath = "//*[@name='processAddress']")
	protected WebElement botaoConfirmarEndereco;

	@FindBy(xpath = "//*[@id='uniform-id_address_delivery']")
	protected WebElement selecionaEndereco;
	
	public ConfirmarEnderecoPage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}

	public void confirmarEderecoEntrega() {
		waitElement(selecionaEndereco, 30);
		Select endereco = new Select(driver.findElement(By.name("id_address_delivery")));
		endereco.selectByVisibleText("My addressa");
		botaoConfirmarEndereco.click();
		log("Confirmar Endereço Entrega");
	}

}