package com.niutex.votingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niutex.votingsystem.entity.Citizen;
import com.niutex.votingsystem.repository.CitizenRepo;

@RestController
public class index {
	
	@Autowired
	CitizenRepo citizenRepo;
	
	@RequestMapping("/doAction")
	public String doAction() {
		
		Citizen citizen = new Citizen((long) 1, "Bob");
		citizenRepo.save(citizen);
		return "Success";
	}

}
