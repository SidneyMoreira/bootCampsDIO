package one.digitalinnovation.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestMethodOrder(MethodOrderer.MethodName.class)
//@TestMethodOrder(MethodOrderer.Random.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)

public class EscolhendoOrdemTest {
    //@Order(4)
    @DisplayName("A")
    @Test
    void validaFluxoA(){
        assertTrue(true);
    }
    //@Order(3)
    @DisplayName("B")
    @Test
    void validaFluxoB(){
        assertTrue(true);
    }
   // @Order(2)
   @DisplayName("C")
    @Test
    void validaFluxoC(){
        assertTrue(true);
    }
    //@Order(3)
    @DisplayName("Teste valida se o usuário foi criado")
    @Test
    void validaFluxoD(){
        assertTrue(true);
    }
}
