package one.digitalinnovation.junit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ContaTest {

    @Test
    void validaSaldo() {
        Conta conta = new Conta("123456", 100);
        assertNotNull(conta);

        conta.lancaCredito(50);

        assertEquals(150, conta.getSaldo());

        conta.lancaDebito(50);

        assertEquals(100, conta.getSaldo());

        assertNotEquals(101, conta.getSaldo());

        conta = null;
        assertNull(conta);
    }
}
