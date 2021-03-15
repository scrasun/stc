package com.scheshire.stc;

import javax.persistence.AttributeConverter;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptionConverter implements AttributeConverter<String, String> {
	
	private static final BasicTextEncryptor encryptor;
	
	static
	{
		encryptor = new BasicTextEncryptor();
		encryptor.setPassword("A_PASSWORD");
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return encryptor.encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return encryptor.decrypt(dbData);
	}

}
