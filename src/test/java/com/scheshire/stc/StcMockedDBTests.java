package com.scheshire.stc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assert;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class StcMockedDBTests {
	
	@MockBean
	private InformationRepo informationRepo;
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@After
	void resetRepo()
	{
		reset(informationRepo);
	}
	
	@Test
	void testReturnIdOnCreate()
	{
		// mock db
		Long mockId = (long) 12;
		Information mockInfo = mock(Information.class);
		when(mockInfo.getId()).thenReturn(mockId);
		when(informationRepo.save(any())).thenReturn(mockInfo);
		
		// Post to api
		IdDTO response = this.restTemplate.postForObject("http://localhost:" + port + "/information", new InformationDTO(), IdDTO.class);
		
		// check mock id returned
		assertThat(response.getId()).isEqualTo(mockId);
	}
	
	@Test
	void testReturnDataOnGet()
	{
		// mock db
		Long mockId = (long) 12;
		String mockName = "name";
		String mockSecrets = "secrets";
		Information mockInfo = mock(Information.class);
		when(mockInfo.getName()).thenReturn(mockName);
		when(mockInfo.getSecrets()).thenReturn(mockSecrets);
		when(informationRepo.existsById(mockId)).thenReturn(true);
		when(informationRepo.getOne(mockId)).thenReturn(mockInfo);
		
		// Get from api
		InformationDTO gotInfo = this.restTemplate.getForObject("http://localhost:" + port + "/information/" + mockId, InformationDTO.class);
		
		// Check data is correct
		assertThat(gotInfo.getSecrets()).isEqualTo(mockSecrets);
		assertThat(gotInfo.getName()).isEqualTo(mockName);
	}
	
	@Test
	void testReturnEmptyOnMissing()
	{
		// mock db
		Long mockId = (long) 12;
		when(informationRepo.existsById(mockId)).thenReturn(false);
		
		// Get from api
		InformationDTO gotInfo = this.restTemplate.getForObject("http://localhost:" + port + "/information/" + mockId, InformationDTO.class);
		
		// Check data is blank
		assertThat(gotInfo.getSecrets()).isNullOrEmpty();;
		assertThat(gotInfo.getName()).isNullOrEmpty();;
	}

}
