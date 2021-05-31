package com.everis.steps;

import com.everis.pages.EscolherFormaTransportePage;

import io.cucumber.java.pt.E;

public class EscolherFormaTransporteSteps {
	
	@E("^escolhe a forma de transporte$")
	public void escolherformaTransporte() {
		EscolherFormaTransportePage escolherFormaTransportePage = new EscolherFormaTransportePage();
		escolherFormaTransportePage.escolherformaTransporte();
	}
	
}