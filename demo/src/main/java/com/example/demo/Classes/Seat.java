package com.example.demo.Classes;

import com.example.demo.Enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "Seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int seatNumber;

    private Boolean isOccupied = false;

    @ManyToOne
    private SeatLayout seatLayoutForeign;
    public synchronized void bookSeat(){
        if (!isOccupied){
            isOccupied = true;
        }
    }

    public synchronized void cancelBooking(){
        if (isOccupied){
            isOccupied = false;
        }
    }

}
