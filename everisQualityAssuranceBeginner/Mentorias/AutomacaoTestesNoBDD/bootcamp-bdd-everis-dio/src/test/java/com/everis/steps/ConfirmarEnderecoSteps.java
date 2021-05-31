package com.everis.steps;

import com.everis.pages.ConfirmarEnderecoPage;

import io.cucumber.java.pt.E;

public class ConfirmarEnderecoSteps {
	
	@E("^confirma o endereco de entrega$")
	public void confirmarEderecoEntrega() {
		ConfirmarEnderecoPage confirmarEnderecoPage = new ConfirmarEnderecoPage();
		confirmarEnderecoPage.confirmarEderecoEntrega();
	}
	
}