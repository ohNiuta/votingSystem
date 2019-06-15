package com.niutex.votingsystem.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="votings")
@Entity
public class Voting {
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Voting() {
		super();
	}

	@Column(name = "name")
	private String votingName;

	public String getVotingName() {
		return votingName;
	}

	public void setVotingName(String votingName) {
		this.votingName = votingName;
	}
	
	@ManyToMany
	private Set<Citizen> participants;

	public Set<Citizen> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Citizen> participants) {
		this.participants = participants;
	}
	
	@OneToMany
	private Set<Candidate> candidates;

	public Set<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}
	
}
