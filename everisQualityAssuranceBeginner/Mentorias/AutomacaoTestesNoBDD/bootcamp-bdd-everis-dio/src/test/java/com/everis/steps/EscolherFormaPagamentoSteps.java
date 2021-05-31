package com.everis.steps;

import com.everis.pages.EscolherFormaPagamentoPage;

import io.cucumber.java.it.Quando;

public class EscolherFormaPagamentoSteps {
	
	@Quando("^o pagamento for confirmando por \"(.*)\"$")
	public void escolherformaPagmento(String formaPagamento) {
		EscolherFormaPagamentoPage escolherFormaPagamentoPage = new EscolherFormaPagamentoPage();
		escolherFormaPagamentoPage.escolherformaPagmento(formaPagamento);
	}

	
}