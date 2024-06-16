package com.example.demo.Classes;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String name;

    @OneToMany(mappedBy = "locationForeign")
    private List<StopLocation> stopLocations = new ArrayList<>();
    //can contain coordinates and so on
}
