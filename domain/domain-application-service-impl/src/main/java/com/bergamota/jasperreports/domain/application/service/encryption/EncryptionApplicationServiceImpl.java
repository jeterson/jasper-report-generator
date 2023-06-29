package com.bergamota.jasperreports.domain.application.service.encryption;

import com.bergamota.jasperreports.domain.application.service.input.services.EncryptionApplicationService;
import com.bergamota.jasperreports.domain.core.exceptions.EncryptionDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionApplicationServiceImpl implements EncryptionApplicationService {

    @Value("${jasper-report-generator.encryption.secret-key:123456abcdfgr}")
    private String secretKey;

    private final CryptoUtil cryptoUtil;

    @Override
    public String encrypt(String plainText) {
        if(plainText == null || plainText.isBlank())
            return null;

        try {
            return cryptoUtil.encrypt(secretKey, plainText);
        }catch (Exception ex){
            throw new EncryptionDomainException(ex.getMessage(), ex);
        }
    }

    @Override
    public String decrypt(String encryptText) {
        if(encryptText == null || encryptText.isBlank())
            return null;

        try {
            return cryptoUtil.decrypt(secretKey, encryptText);
        }catch (Exception ex){
            throw new EncryptionDomainException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean areEqual(String plainText, String encryptionText) {
        var decryptionText = decrypt(encryptionText);
        if(plainText == null)
            return false;

        return plainText.equals(decryptionText);
    }
}
