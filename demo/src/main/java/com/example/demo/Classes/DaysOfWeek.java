package com.example.demo.Classes;

import com.example.demo.Enums.Days;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "DaysOfWeek")
public class DaysOfWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;

    private String day;

    @ManyToMany(mappedBy = "daysOfOperation")
    private List<Bus> buses = new ArrayList<>();
}
