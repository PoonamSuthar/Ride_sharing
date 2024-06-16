package com.example.demo.Repositories;

import com.example.demo.Classes.SeatLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatLayoutRepo extends JpaRepository<SeatLayout,Long> {
}
