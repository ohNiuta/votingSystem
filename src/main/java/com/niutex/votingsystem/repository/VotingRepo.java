package com.niutex.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niutex.votingsystem.entity.Voting;

@Repository
public interface VotingRepo extends JpaRepository<Voting, Integer>{

}

