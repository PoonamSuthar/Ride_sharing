package com.example.demo.Classes;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "StopLocation")
public class StopLocation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "stopId")
    private Long stopId;

    @ManyToOne
    private Location locationForeign;

    private Time eta;

    @ManyToOne
    private Bus busForeign;
}
