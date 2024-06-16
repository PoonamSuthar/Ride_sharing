package com.example.demo.Classes;

import com.example.demo.Enums.Days;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;

    private String name;

    private int totalSeats;

    @OneToOne
    private SeatLayout seatLayout;

    @ManyToMany
    @JoinTable(
            name = "bus_days",
            joinColumns = @JoinColumn(name = "bus_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id")
    )
    private List<DaysOfWeek> daysOfOperation;

    //busRoute
    @OneToMany(mappedBy = "busForeign")
    private List<StopLocation> busRoute = new ArrayList<>();


    //image for plan of seat layout
    public boolean routeContains(Location loc){
        for (StopLocation it: busRoute){
            if (it.getLocationForeign().equals(loc)){
                return true;
            }
        }
        return  false;
    }


}
