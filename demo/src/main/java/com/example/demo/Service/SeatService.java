package com.example.demo.Service;

import com.example.demo.Classes.Seat;
import com.example.demo.Repositories.SeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {
    @Autowired
    SeatRepo seatRepo;

    public Boolean bookSeat(Long seatId){
        Optional<Seat> seatToBook = seatRepo.findBySeatId(seatId);
        if (seatToBook.isEmpty()){
            throw new RuntimeException("No seat exists with seatId: " + seatId);
        }
        Seat seat = seatToBook.get();
        seat.bookSeat();
        seat = seatRepo.save(seat);
        if (!seat.getIsOccupied()){
            throw new RuntimeException("Failed to book seat");
        }
        return true;
    }
}
