package com.everis.steps;

import com.everis.pages.FazerLoginPage;

import io.cucumber.java.pt.E;

public class FazerLoginSteps {
	
	@E("^realiza o login$")
	public void fazerLoginUsuario() {
		FazerLoginPage fazerLoginPage = new FazerLoginPage();
		fazerLoginPage.fazerLoginUsuario();
	}
	
}