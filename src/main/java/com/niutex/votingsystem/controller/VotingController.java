package com.niutex.votingsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.niutex.votingsystem.entity.Candidate;
import com.niutex.votingsystem.entity.Citizen;
import com.niutex.votingsystem.repository.CandidateRepo;
import com.niutex.votingsystem.repository.CitizenRepo;

//@RestController
//diplays only text, not html template/file
@Controller
public class VotingController {
	
	@Autowired
	CitizenRepo citizenRepo;
	
	@Autowired
	CandidateRepo candidateRepo;
	
	@RequestMapping("/")
	public String goToVote() {
		
		return "index.html";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam String name, Model model) {
		
		Citizen citizen = citizenRepo.findByName(name);
		model.addAttribute("citizen", citizen);
		
		List<Candidate> candidates= candidateRepo.findAll();
		model.addAttribute("candidates",candidates);
		
		if(!citizen.getHasVoted()) {
			
			return "performToVote.html";
		}
		else {
			return "alreadyVoted.html" ;
		}
		
	}
	
	@RequestMapping("/voteFor/{id}")
	public String voteFor(@PathVariable Integer id) {
		return null;
		
	}

}
