package com.everis.steps;

import org.junit.Assert;

import com.everis.pages.FinalizaPage;

import io.cucumber.java.pt.Entao;

public class FinalizaSteps {
	
	
	@Entao("^deve ser apresentado a mensagem \"(.*)\"$")
	public void aMensagemEsperada(String mensagemFinal) {
		FinalizaPage finalizaPage = new FinalizaPage();
		Assert.assertTrue("A mensagem esperada é [" + mensagemFinal + "]", 
				finalizaPage.aMensagemEsperada(mensagemFinal));
	}
}