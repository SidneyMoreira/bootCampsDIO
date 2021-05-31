package com.everis.steps;

import com.everis.pages.HomePage;
import com.everis.util.Hooks;

import atu.testrecorder.exceptions.ATUTestRecorderException;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;

public class HomeSteps {

	@Dado("^que um usuario acessa o site \"(.*)\"$")
	public void acessarSite(String url) throws ATUTestRecorderException {
		Hooks.navigateToULRChrome(url);
	}
	
	@E("^pesquisa pelo produto \"(.*)\"$")
	public void pesquisarProduto(String nomeProduto) {
		HomePage homePage = new HomePage();
		homePage.pesquisarProduto(nomeProduto);
	}
	
}