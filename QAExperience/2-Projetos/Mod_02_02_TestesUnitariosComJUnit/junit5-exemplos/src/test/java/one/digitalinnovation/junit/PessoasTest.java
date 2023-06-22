package one.digitalinnovation.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PessoasTest {

    @Test
    void deveCalcularIdadeCorretamente(){
        Pessoa jessica = new Pessoa("Jessica", LocalDate.of(2000,1,1));
        Assertions.assertEquals(23, jessica.getIdade());
    }

    @Test
    void deveRetornarSeEhMaiorDeIdade(){
        Pessoa jessica = new Pessoa("Jessica", LocalDate.of(2000,1,1));
        Assertions.assertTrue(jessica.ehMaiorDeIdade());
    }
    @Test
    void validarCalculoDeIdade(){
        Pessoa pessoa = new Pessoa("Sidney", LocalDate.of(1975,12,11));
        Assertions.assertEquals(47, pessoa.getIdade());
    }
}
