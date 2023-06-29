package com.bergamota.jasperreports.domain.application.service.input.services;

public interface EncryptionApplicationService {
    String encrypt(String plaintext);
    String decrypt(String encryptText);
    boolean areEqual(String plainText, String encryptionText);
}
