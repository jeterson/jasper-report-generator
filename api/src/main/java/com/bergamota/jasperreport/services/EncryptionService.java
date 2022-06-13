package com.bergamota.jasperreport.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.utils.CryptoUtil;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class EncryptionService {

	@Value("${jasper-report-generator.encryption.secret-key}")
	private String secretKey;
	
	private final CryptoUtil cryptoUtil;
	
	@SneakyThrows
	public String encrypt(String plainText) {
		if(plainText == null || plainText.isBlank())
			return null;
		return cryptoUtil.encrypt(secretKey, plainText);
	}
	
	@SneakyThrows
	public String decrypt(String encryptText) {
		if(encryptText == null || encryptText.isBlank())
			return null;
		return cryptoUtil.decrypt(secretKey, encryptText);
	}
}
