package com.scheshire.stc;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class StcApplicationTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void testPostAndRetreive()
	{
		// Mock data
		String name = "name";
		String secrets = "secrets";
		InformationDTO info = new InformationDTO();
		info.setName(name);
		info.setSecrets(secrets);
		// Post to api
		IdDTO response = this.restTemplate.postForObject("http://localhost:" + port + "/information", info, IdDTO.class);
		Long id = response.getId();
		
		assertThat(id).isPositive();
		
		// Get from api
		InformationDTO gotInfo = this.restTemplate.getForObject("http://localhost:" + port + "/information/" + id, InformationDTO.class);
		
		// Check data is correct
		assertThat(gotInfo.getSecrets()).isEqualTo(secrets);
		assertThat(gotInfo.getName()).isEqualTo(name);
	}
	
	@Test
	void testPostAndRetreiveNullData()
	{
		// Mock data
		String name = null;
		String secrets = null;
		InformationDTO info = new InformationDTO();
		info.setName(name);
		info.setSecrets(secrets);
		// Post to api
		IdDTO response = this.restTemplate.postForObject("http://localhost:" + port + "/information", info, IdDTO.class);
		Long id = response.getId();
		
		assertThat(id).isPositive();
		
		// Get from api
		InformationDTO gotInfo = this.restTemplate.getForObject("http://localhost:" + port + "/information/" + id, InformationDTO.class);
		
		// Check data is correct
		assertThat(gotInfo.getSecrets()).isEqualTo(secrets);
		assertThat(gotInfo.getName()).isEqualTo(name);
	}
	
	@Test
	void testPostAndRetreiveBlankData()
	{
		// Mock data
		String name = "";
		String secrets = "";
		InformationDTO info = new InformationDTO();
		info.setName(name);
		info.setSecrets(secrets);
		// Post to api
		IdDTO response = this.restTemplate.postForObject("http://localhost:" + port + "/information", info, IdDTO.class);
		Long id = response.getId();
		
		assertThat(id).isPositive();
		
		// Get from api
		InformationDTO gotInfo = this.restTemplate.getForObject("http://localhost:" + port + "/information/" + id, InformationDTO.class);
		
		// Check data is correct
		assertThat(gotInfo.getSecrets()).isEqualTo(secrets);
		assertThat(gotInfo.getName()).isEqualTo(name);
	}
	
	@Test
	void testEncryptDecryptData()
	{
		String inputString = "Some words";
		EncryptionConverter ec = new EncryptionConverter();
		
		// encrypt and check new string is different
		String encrypted = ec.convertToDatabaseColumn(inputString);
		assertThat(encrypted).isNotNull().isNotBlank().isNotEqualTo(inputString);
		
		// decrypt and check matches original
		String decrypted = ec.convertToEntityAttribute(encrypted);
		assertThat(decrypted).isEqualTo(inputString);
	}

}
