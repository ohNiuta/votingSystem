package com.niutex.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niutex.votingsystem.entity.Citizen;

@Repository
public interface CitizenRepo extends JpaRepository<Citizen, Integer> {

}
