package com.niutex.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niutex.votingsystem.entity.Candidate;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Integer> {

	 public Candidate findById(long id);

}
