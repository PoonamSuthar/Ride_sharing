package com.example.demo.Repositories;

import com.example.demo.Classes.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepo extends JpaRepository<Bus, Long> {
    Optional<Bus> findByBusId(Long id);
}
