package com.example.demo.Repositories;

import com.example.demo.Classes.StopLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopLocationRepo extends JpaRepository<StopLocation, Long> {
}
