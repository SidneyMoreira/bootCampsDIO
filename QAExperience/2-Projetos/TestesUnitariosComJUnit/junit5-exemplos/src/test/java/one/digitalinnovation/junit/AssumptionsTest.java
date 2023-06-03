package one.digitalinnovation.junit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class AssumptionsTest {

    @Test
    void validarAlgoSomenteNoUsuarioSidney(){
        assumeTrue("kadarpegasus".equals(System.getenv("USER")));
        assertEquals(10, 5+5);
    }
}
