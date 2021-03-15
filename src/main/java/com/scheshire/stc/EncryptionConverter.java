package com.scheshire.stc;

import javax.persistence.AttributeConverter;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * DB data encryptor
 */
public class EncryptionConverter implements AttributeConverter<String, String> {
	
	// Encryptor
	private static final BasicTextEncryptor encryptor;
	
	static
	{
		encryptor = new BasicTextEncryptor();
		encryptor.setPassword("A_PASSWORD");
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		// encrypt data to be written to db
		return encryptor.encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		// decrypt data on read
		return encryptor.decrypt(dbData);
	}

}
