package com.example.demo.Classes;

import com.example.demo.Enums.SeatType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@DiscriminatorValue("LUXURY")
public class LuxurySeat extends Seat{

    public LuxurySeat() {
        super();
    }

}
