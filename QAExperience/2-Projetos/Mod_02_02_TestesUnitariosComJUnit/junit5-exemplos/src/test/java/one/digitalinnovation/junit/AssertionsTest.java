package one.digitalinnovation.junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AssertionsTest {

    @Test
    void validarLancamentos(){
        int[] primeiroLancamento = {10, 30, 40, 50, 60};
        int[] segundoLancamento = {10, 30, 40, 50, 60};

        assertArrayEquals(primeiroLancamento, segundoLancamento);
    }

    @Test
    void validarSeObjetoEstaNulo(){
        Pessoa pessoa = null;

       assertNull(pessoa);

        pessoa = new Pessoa("sidney", LocalDate.now());
        assertNotNull(pessoa);
    }

    @Test
    void validarNumerosDeTiposDiferentes(){
        double valor = 5.0;
        double outroValor = 5.0;

        assertEquals(valor,outroValor);
    }
}
