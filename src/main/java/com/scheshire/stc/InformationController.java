package com.scheshire.stc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * API controller
 */
@RestController
public class InformationController {
	@Autowired
	private InformationRepo infoRepo;
	
	/**
	 * Get a record from db
	 * @param id The id of the record to fetch
	 * @return The information in the record
	 */
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
	
	/**
	 * Add a new record
	 * @param input The information to add
	 * @return An object containing the id of the new record
	 */
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
