package com.example.readConnect.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntrestRepository extends JpaRepository<Intrest, Long> {
}