package com.example.demo.Classes;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "SeatLayout")
public class SeatLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long layoutId;

    @OneToOne(mappedBy = "seatLayout")
    private Bus bus;

    @OneToMany(mappedBy = "seatLayoutForeign")
    private List<Seat> seats = new ArrayList<>();
}
