package ar.com.tebbianonizza;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CryptResourceTest {

    @Test
    public void testDecryptEndpoint() {
        // Simula la lógica de cifrado en tu servicio (CryptoHandlerService)
        // Puedes utilizar Mockito u otras herramientas para crear mocks y simular comportamientos
        // Mockito.when(cryptoHandlerService.metodoDelServicio()).thenReturn(resultadoEsperado);

        given()
            .when().post("/api/crypt")
            .then()
                .statusCode(200)
                .body("encryptedValue", equalTo("valorEsperado")); // Ajusta según el resultado esperado
    }

    // Puedes agregar más pruebas según sea necesario
}
