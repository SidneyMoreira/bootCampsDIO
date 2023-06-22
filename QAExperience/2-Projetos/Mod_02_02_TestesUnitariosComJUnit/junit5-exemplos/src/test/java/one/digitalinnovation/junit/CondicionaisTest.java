package one.digitalinnovation.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static org.junit.jupiter.api.condition.JRE.*;

public class CondicionaisTest {

    @Test
    //@EnabledIfEnvironmentVariable(named = "USER", matches = "kadarpegasus")
    //@EnabledOnOs({OS.MAC, OS.LINUX})
    //@EnabledOnJre(JAVA_17)
    @EnabledForJreRange(min = JAVA_15, max = JAVA_17)
    void validarAlgoSomenteNoUsuarioSidney(){
        assertEquals(10, 5+5);
    }
}
