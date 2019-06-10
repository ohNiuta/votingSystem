package com.niutex.votingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="votings")
@Entity
public class Voting {
	
	@Id
	@Column(name="id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="citizen_id")
	private Long citizenId;

	public Long getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(Long citizenId) {
		this.citizenId = citizenId;
	}

	public Voting(Long id, Long citizenId) {
		super();
		this.id = id;
		this.citizenId = citizenId;
	}
	
	public Voting() {
		super();
	}
	
	
	

}
