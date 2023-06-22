package one.digitalinnovation.junit;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
public class ExceptionsTest {

    @Test
    void validadeCenarioDeExcecaoNaTransferencia(){
        Conta contaOrigem = new Conta("1234567", 0);
        Conta contaDestino = new Conta("654321", 100);

        TransferenciaEntreContas transferenciaEntreContas = new TransferenciaEntreContas();

        /*assertThrows(IllegalArgumentException.class,() ->
                transferenciaEntreContas.transfere(contaOrigem, contaDestino, -1));*/

        assertDoesNotThrow(() ->
                transferenciaEntreContas.transfere(contaOrigem, contaDestino, 20));
    }

}
