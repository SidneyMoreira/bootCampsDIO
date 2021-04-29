package poo.model;

import javax.management.RuntimeErrorException;

public class Pessoa {
	
	private static final int TAMANHO_CPF = 11;
	private static final int TAMANHO_CNPJ = 12; 
	
	public enum TipoPessoa {FISICA, JURIDICA}
	
	public Integer codigo;
	public String nome;
	private String documento;
	public TipoPessoa tipo;
	
	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String documento) {
		if(documento == null || documento.isEmpty()) {
			throw new RuntimeErrorException(null, "Documento não pode ser nulo ou vazio!!!");
		}
		if (documento.length() == TAMANHO_CPF) {
			setDocumento(documento, tipo = TipoPessoa.FISICA);
		} else if (documento.length() == TAMANHO_CNPJ) {
			setDocumento(documento, tipo = TipoPessoa.JURIDICA);
		} else {
			throw new RuntimeErrorException(null, "Documento invalido para pessoa fisica ou juridica");
		}
		
		
	}
	
	private void setDocumento(String documento, TipoPessoa tipo) {
		this.documento = documento;
		this.tipo = tipo;
	}
	
	public TipoPessoa getTipo() {
		return tipo;
	}

}
