package ar.com.tebb;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import ar.com.tebb.Entity.CryptoHandler;
import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.DatatypeConverter;

/**
 * Endpoint REST para manejar operaciones de cifrado y descifrado.
 */
@Path("/api")
public class CryptResource {

    /**
     * Endpoint para descifrar un valor encriptado utilizando la información proporcionada en el objeto CryptoHandler.
     * 
     * @param cryptoHandler Objeto que contiene la información necesaria para el descifrado.
     * @return El objeto CryptoHandler con el valor descifrado.
     */
    @POST
    @Path("/crypt")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response decryptValue(CryptoHandler cryptoHandler) {
        // Crear la clave secreta basada en la información proporcionada en el objeto CryptoHandler
        SecretKeySpec secretKeySpec = new SecretKeySpec(DatatypeConverter.parseHexBinary(cryptoHandler.getKey()),
                cryptoHandler.getAlgorithm());

        byte[] original = null;
        Cipher cipher = null;

        try {
            // Configurar el cifrado con el modo especificado en el objeto CryptoHandler
            cipher = Cipher.getInstance(cryptoHandler.getEncryptMode());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            // Descifrar el valor encriptado y asignarlo al objeto CryptoHandler
            original = cipher.doFinal(DatatypeConverter.parseHexBinary(cryptoHandler.getEncryptedValue()));
            cryptoHandler.setDecryptedValue(new String(original, StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            Log.error("Error: Algoritmo de cifrado no encontrado.", e);
            // Manejar de acuerdo a los requisitos de tu aplicación
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error interno del servidor").build();
        } catch (NoSuchPaddingException e) {
            Log.error("Error: Relleno (Padding) no encontrado.", e);
            // Manejar de acuerdo a los requisitos de tu aplicación
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error interno del servidor").build();
        } catch (InvalidKeyException e) {
            Log.error("Error: Clave inválida.", e);
            // Manejar de acuerdo a los requisitos de tu aplicación
            return Response.status(Response.Status.BAD_REQUEST).entity("Clave inválida").build();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            Log.error("Error: Tamaño de bloque ilegal o relleno incorrecto.", e);
            // Manejar de acuerdo a los requisitos de tu aplicación
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error interno del servidor").build();
        }

        // Devolver el objeto CryptoHandler con el valor descifrado
        return Response.ok(cryptoHandler).build();
    }
}