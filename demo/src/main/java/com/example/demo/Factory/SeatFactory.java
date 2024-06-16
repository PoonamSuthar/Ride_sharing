package com.example.demo.Factory;

import com.example.demo.Classes.LuxurySeat;
import com.example.demo.Classes.NormalSeat;
import com.example.demo.Classes.Seat;

public class SeatFactory {
    public static Seat getSeat(String type){
        switch (type){
            case "Normal":
                return new NormalSeat();
            case "Luxury":
                return new LuxurySeat();
            default:
                throw new IllegalArgumentException("Unknown seat type.");
        }
    }
}
