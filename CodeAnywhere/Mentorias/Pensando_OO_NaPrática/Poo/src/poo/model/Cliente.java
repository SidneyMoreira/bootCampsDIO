package poo.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa{
	
	public String numCartao;
	private List<Endereco> endereco;
	
	public void adicionaEndereco(Endereco endereco) {
		if (endereco == null) {
			throw new RuntimeException("Endereço nao pode ser nulo");
		}
		
		if (endereco.cep == null) {
			throw new RuntimeException("CEP nao pode ser nulo");
		}
		getEndereco().add(endereco);
	}
	
	private List<Endereco> getEndereco() {
		if(endereco == null) {
			endereco = new ArrayList<Endereco>();
		}
		return endereco;
	}
	

}
