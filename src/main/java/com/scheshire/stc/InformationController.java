package com.scheshire.stc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationController {
	@Autowired
	private InformationRepo infoRepo;
	
	@GetMapping("/information/{id:.+}")
	public InformationDTO getInformation(@PathVariable Long id)
	{
		InformationDTO response = new InformationDTO();
		
		if (!infoRepo.existsById(id))
		{
			System.out.println("nya");
			return response;
		}
			
		Information info = infoRepo.getOne(id);
		
		response.setName(info.getName());
		response.setSecrets(info.getSecrets());
		
		return response;
	}
	
	@PostMapping("/information")
	public IdDTO postInformation(@RequestBody InformationDTO input)
	{
		Information info = new Information();
		info.setName(input.getName());
		info.setSecrets(input.getSecrets());
		
		IdDTO response = new IdDTO();
		
		response.setId(infoRepo.save(info).getId());
		
		return response;
	}

}
