package com.niutex.votingsystem.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="citizens")
@Entity
public class Citizen {
	
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

	@Column(name="citizen_name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Citizen() {
		super();
	}
	
	@ManyToMany
	private Set<Voting> participations;

	public Set<Voting> getParticipations() {
		return participations;
	}

	public void setParticipations(Set<Voting> participations) {
		this.participations = participations;
	}
	
	@OneToMany
	private Set<Voting> ownedVotings;

	public Set<Voting> getOwnedVotings() {
		return ownedVotings;
	}

	public void setOwnedVotings(Set<Voting> ownedVotings) {
		this.ownedVotings = ownedVotings;
	}

}
