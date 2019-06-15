package com.niutex.votingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="candidates")
@Entity
public class Candidate {
	
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
	
	@Column(name="candidate_name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="number_of_votes")
	private Integer numberOfVotes;

	public Integer getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(Integer numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public Candidate() {
		super();
	}
	
	@ManyToOne
	private Voting voting;

	public Voting getVoting() {
		return voting;
	}

	public void setVoting(Voting voting) {
		this.voting = voting;
	}

}
