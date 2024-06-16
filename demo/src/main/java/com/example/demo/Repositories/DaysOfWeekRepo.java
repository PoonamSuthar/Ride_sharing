package com.example.demo.Repositories;

import com.example.demo.Classes.DaysOfWeek;
import com.example.demo.Enums.Days;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysOfWeekRepo extends JpaRepository<DaysOfWeek,Long> {
    DaysOfWeek findByDay(String day);
}
