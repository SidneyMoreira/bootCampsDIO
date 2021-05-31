package com.everis.steps;

import com.everis.pages.AcessaCheckoutPage;

import io.cucumber.java.pt.E;

public class AcessaCheckoutSteps {
	
	@E("^acessa o checkout$")
	public void fazerCheckoutProduto() {
		AcessaCheckoutPage acessaCheckoutPage = new AcessaCheckoutPage();
		acessaCheckoutPage.fazerCheckoutProduto();
	}
	
}