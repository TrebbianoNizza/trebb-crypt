package ar.com.tebb.Entity;

import lombok.Data;

@Data
public class CryptoHandler {
    private String algorithm;
    private String encryptMode;
    private String encryptedValue;
    private String decryptedValue;
    private String key;
}
