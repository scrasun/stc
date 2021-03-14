package com.scheshire.stc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationController {
	@Autowired
	private InformationRepo infoRepo;
	
	@GetMapping("/information")
	public String getInformation(@RequestParam Long id)
	{
		return infoRepo.getOne(id).getName();
	}
	
	@PostMapping("/information")
	public String postInformation(@RequestParam String name, @RequestParam String secrets)
	{
		Information info = new Information();
		info.setName(name);
		info.setSecrets(secrets);
		return infoRepo.save(info).getId().toString();
	}

}
