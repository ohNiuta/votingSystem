package com.niutex.votingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="citizens")
@Entity
public class Citizen {
	
	@Id
	@Column(name="id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="citizen_name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="hasvoted")
	private Boolean hasVoted;

	public Boolean getHasVoted() {
		return hasVoted;
	}

	public void setHasVoted(Boolean hasVoted) {
		this.hasVoted = hasVoted;
	}
	
	@Column(name="votingOwner")
	private Boolean hasSetVoting;

	public Boolean getHasSetVoting() {
		return hasSetVoting;
	}

	public void setHasSetVoting(Boolean hasSetVoting) {
		this.hasSetVoting = hasSetVoting;
	}
	
	public Citizen(Long id, String name, Boolean hasVoted, Boolean hasSetVoting) {
		super();
		this.id = id;
		this.name = name;
		this.hasVoted = hasVoted;
		this.hasSetVoting = hasSetVoting;
	}

	public Citizen() {
		super();
	}

}
