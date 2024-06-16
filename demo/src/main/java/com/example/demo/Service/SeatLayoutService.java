package com.example.demo.Service;

import com.example.demo.Classes.Seat;
import com.example.demo.Classes.SeatLayout;
import com.example.demo.Factory.SeatFactory;
import com.example.demo.Factory.SeatLayoutFactory;
import com.example.demo.Repositories.SeatLayoutRepo;
import com.example.demo.Repositories.SeatRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//abstract factory
@Service
public class SeatLayoutService {
    private final Logger LOGGER = LoggerFactory.getLogger(SeatLayoutService.class);

    @Autowired
    SeatLayoutRepo seatLayoutRepo;
    @Autowired
    SeatRepo seatRepo;
    public SeatLayout getSeatLayout(String type, int number){
        LOGGER.info("creating seat layout for {} type and {} seats", type, number);
        SeatLayout seatLayout =  new SeatLayout();
        seatLayout = seatLayoutRepo.save(seatLayout);

        for (int i=0; i<number; i++){
            Seat seat = SeatFactory.getSeat(type);
            seat.setSeatLayoutForeign(seatLayout);
            seat.setSeatNumber(i+1);
            seat.setIsOccupied(false);
            seat = seatRepo.save(seat);
            seatLayout.getSeats().add(seat);
        }
        seatLayout = seatLayoutRepo.save(seatLayout);
        LOGGER.info("seatLayout size: " + seatLayout.getSeats().size());

        return seatLayout;
    }
}
