package com.niutex.votingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="candidates")
@Entity
public class Candidate {
	
	@Id
	@Column(name="id")
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
	
	@Column(name="votnig_id")
	private Long votingId;
	

	public Long getVotingId() {
		return votingId;
	}

	public void setVotingId(Long votingId) {
		this.votingId = votingId;
	}

	public Candidate(Long id, String name, Integer numberOfVotes, Long votingId) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfVotes = numberOfVotes;
		this.votingId = votingId;
	}

	
	public Candidate() {
		super();
	}
	
	
	
	

}
