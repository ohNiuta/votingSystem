package com.niutex.votingsystem.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	public final Logger logger = Logger.getLogger(VotingController.class);
	
	@Autowired
	CitizenRepo citizenRepo;
	
	@Autowired
	CandidateRepo candidateRepo;
	
	@RequestMapping("/")
	public String goToVote() {
		
		logger.info("Returnig index.html file");
		
		return "index.html";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam String name, Model model, HttpSession session) {
		
		logger.info("Getting citizen grom database");		
		Citizen citizen = citizenRepo.findByName(name);
		
		logger.info("Settinf citizen into session");
		session.setAttribute("citizen", citizen);
		model.addAttribute("citizen", citizen);
		
		if(!citizen.getHasVoted()) {
			logger.info("Putting candidates into model");
			List<Candidate> candidates= candidateRepo.findAll();
			model.addAttribute("candidates",candidates);
			return "performToVote.html";
		}
		else {
			return "alreadyVoted.html" ;
		}
		
	}
	
	@RequestMapping("/voteFor")
	public String voteFor(Long id, HttpSession session, Model model) {
		
		Citizen citizen = (Citizen)session.getAttribute("citizen");
		
		if (!citizen.getHasVoted()) {
			
			logger.info("Citizen " + citizen.getName() + " has voted");
			citizen.setHasVoted(true);
						
			Candidate candidate = candidateRepo.findById((long) id);
			logger.info("Voting for candidate" + candidate.getName());		
			candidate.setNumberOfVotes((candidate.getNumberOfVotes()) + 1);
			
			citizenRepo.save(citizen);
			candidateRepo.save(candidate);
			
			model.addAttribute("citizen", citizen);
			
			return "voted.html";
		}
		
		return "alreadyVoted.html";
		
		
		
		
	}

}
