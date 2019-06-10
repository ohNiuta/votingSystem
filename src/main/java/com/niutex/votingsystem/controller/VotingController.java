package com.niutex.votingsystem.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niutex.votingsystem.entity.Candidate;
import com.niutex.votingsystem.entity.Citizen;
import com.niutex.votingsystem.entity.Voting;
import com.niutex.votingsystem.repository.CandidateRepo;
import com.niutex.votingsystem.repository.CitizenRepo;
import com.niutex.votingsystem.repository.VotingRepo;

//@RestController
//diplays only text, not html template/file
@Controller
public class VotingController {
	
	public final Logger logger = Logger.getLogger(VotingController.class);
	
	@Autowired
	CitizenRepo citizenRepo;
	
	@Autowired
	CandidateRepo candidateRepo;
	
	@Autowired
	VotingRepo votingRepo;
	
	@RequestMapping("/")
	public String goToVote() {
		
		logger.info("Returnig index.html file");
		
		return "index.html";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam String name, @RequestParam String choice, Model model,
			HttpSession session, RedirectAttributes redirectAttr) {
		
		logger.info("Getting citizen from database");		
		Citizen citizen = citizenRepo.findByName(name);
		
		logger.info("Getting votings from database");
		List<Voting> votings = votingRepo.findAll();
		
		if (!votings.isEmpty()) {
			
			try {
				logger.info("Setting citizen into session");
				session.setAttribute("citizen", citizen);
				model.addAttribute("citizen", citizen);
				
				if (choice == "goVote") {
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
				
				else if (choice == "checkResult") {
					if(citizen.getHasSetVoting()) {
						return "checkResult.html";
					}
					else {
						redirectAttr.addFlashAttribute("flashMessage", "Nie masz jeszcze swoich głosowań. Załóż swoje głosowanie");
						return "redirect:/";
					}
				}
				
				}
				catch (NullPointerException e){
					if(citizen == null) {
						Citizen newCitizen = new Citizen();
						citizenRepo.save(newCitizen);
						redirectAttr.addFlashAttribute("flashMessage", "Witaj!");
						   return "redirect:/";
					}
					
				}
			
		}
		
		
		
		Voting voting = new Voting();
		votingRepo.save(voting);
		return "setVoting.html";
		
		
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
