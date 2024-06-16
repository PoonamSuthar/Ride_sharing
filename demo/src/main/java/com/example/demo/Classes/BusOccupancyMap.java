package com.example.demo.Classes;

import lombok.*;

import java.util.Map;
@Data

public class BusOccupancyMap {
    Long busId;
    Map<Seat, CustomUser> seatToUser;
}
