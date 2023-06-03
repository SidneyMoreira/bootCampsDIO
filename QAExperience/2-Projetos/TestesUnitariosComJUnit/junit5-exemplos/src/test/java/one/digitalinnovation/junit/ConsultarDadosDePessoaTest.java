package one.digitalinnovation.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultarDadosDePessoaTest {

    @BeforeAll
    static void configuraConexao(){
        BancoDeDados.iniciarConexao();

    }

    @AfterAll
    @De
    static void encerraConexao(){
        BancoDeDados.finalizarConexao();
        System.out.println("Fechou a configuração");
    }

    @Test
    void validarDadosDeRetorno(){
        assertTrue(true);

    }

}
