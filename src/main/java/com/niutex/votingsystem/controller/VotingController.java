package com.niutex.votingsystem.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
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
	public String doLogin(@RequestParam String name, 
			@RequestParam String choice,
			Model model, HttpSession httpSession, RedirectAttributes redirectAttr) {
			
		Citizen citizen = citizenRepo.findByName(name);
		logger.info("Getting citizen from database");	
		
		if (citizen == null) {
			Citizen newCitizen = new Citizen();
			newCitizen.setName(name);
			citizenRepo.save(newCitizen);
			logger.info("New citizen added to database");
			citizen = newCitizen;
		}
		
		httpSession.setAttribute("citizen", citizen);
		logger.info("Setting citizen into session");
		
		Set<Voting> votings = citizen.getOwnedVotings();
		model.addAttribute("votings", votings);
								
		if (choice.matches("goVote")) {
			try {
				Map<Voting, Set<Candidate>> myVotings = new HashMap<>();
				Set<Candidate> candidates = new HashSet<>();
				for (Voting voting : votings) {
					candidates = voting.getCandidates();
					myVotings.put(voting, candidates);				
				}
				model.addAttribute("myVotings", myVotings);
				logger.info("Putting myVotings into model");
				
				return "/performToVote.html";
			} catch (Exception e) {
				redirectAttr.addFlashAttribute("flashMessage", "Lista głosowań jest pusta");
				return "redirect:/index.html";
			}
			
		}
		
		else if (choice.matches("setVoting")) {
			return "votingDetails.html";
		}
		else {
//			choice.matches(checkResult.html)
			try {
				return "checkResults.html";
						
			} catch (Exception e) {
				redirectAttr.addFlashAttribute("flashMessage", "Nie masz jeszcze swoich głosowań. Załóż swoje głosowanie");
				return "redirect:/";
			}
		}
	}
	
	@RequestMapping("/voteFor")
	public String voteFor(@RequestParam Long id, HttpSession session, Model model) {
		
		Citizen citizen = (Citizen)session.getAttribute("citizen");
		model.addAttribute("citizen", citizen);
		Candidate candidate = candidateRepo.findById((long) id);
		Voting voting = candidate.getVoting();
		
		Set<Citizen> citizens = voting.getParticipants();
//		LazyInitializationException
		Set<Voting> participations = new HashSet<>();
		for (Citizen citizenToUpdate : citizens) {
			if(citizenToUpdate.getId() == (citizen.getId())) {
				participations = citizenToUpdate.getParticipations();
			}
		}
		
		if (!participations.isEmpty()) {
			for (Voting votingToFind : participations) {
				if (voting.getId().equals(votingToFind.getId())) {
					return "alreadyVoted.html";
				}
			}
		}
		
		try {
			candidate.setNumberOfVotes((candidate.getNumberOfVotes()) + 1);
		}
		catch (NullPointerException e){
			candidate.setNumberOfVotes(1);
		}
		logger.info("Candidate " + candidate.getName() + " has "
		+ candidate.getNumberOfVotes() + " votes totally");	
		
		
		participations.add(voting);
		citizen.setParticipations(participations);
		
		candidateRepo.save(candidate);
		citizenRepo.save(citizen);
		
		return "voted.html";
	}
	
	@RequestMapping("/setVoting")
	public String setVoting(@RequestParam String name,
			@RequestParam String candidate1, @RequestParam String candidate2,
			@RequestParam String candidate3, HttpSession session,
			RedirectAttributes redirectAttributes, Model model) {
		
		Voting voting = new Voting();
		Citizen citizen = (Citizen) session.getAttribute("citizen");
		
		voting.setVotingName(name);
//		w tabeli citizens_participations będę zapisywać dopiero jak zagłosują
//		TODO zapraszanie oosób do głosowania
		try {
			voting.getParticipants().add(citizen);
		} catch (NullPointerException e) {
			Set<Citizen> newCitizensSet = new HashSet<>();
			newCitizensSet.add(citizen);
			voting.setParticipants(newCitizensSet);
		}
		votingRepo.saveAndFlush(voting);
		Set<Candidate> candidates = new HashSet<>();

		
		Candidate newCandidate1 = new Candidate();
		newCandidate1.setName(candidate1);
		newCandidate1.setVoting(voting);
		candidates.add(newCandidate1);
		Candidate newCandidate2 = new Candidate();
		newCandidate2.setName(candidate2);
		newCandidate2.setVoting(voting);
		candidates.add(newCandidate2);
		Candidate newCandidate3 = new Candidate();
		newCandidate3.setName(candidate3);
		newCandidate3.setVoting(voting);
		candidates.add(newCandidate3);
		
		candidateRepo.saveAll(candidates);
		logger.info("New candidate saved to database");
		
		voting.setCandidates(candidates);
		votingRepo.save(voting);
		logger.info("New voting saved to database");	
		
		try {
			citizen.getOwnedVotings().add(voting);
		} catch (Exception e) {
			Set<Voting> newVotingsSet = new HashSet<>();
			newVotingsSet.add(voting);
			citizen.setOwnedVotings(newVotingsSet);
		}
		citizenRepo.save(citizen);

//		model.addAttribute("message", "Dodałeś szczegóły głosowania");
		redirectAttributes.addFlashAttribute("flashMessage", "Dodałeś nowe głosowanie");

		return "redirect:/votingDetails.html";
	}
	
	@RequestMapping
	public String checkResulst() {
		return null;
	}
	
}
