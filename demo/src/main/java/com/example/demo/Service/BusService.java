package com.example.demo.Service;

import com.example.demo.Classes.*;
import com.example.demo.Repositories.BusRepo;
import com.example.demo.Repositories.LocationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

@Service
public class BusService {
    private final Logger LOGGER = LoggerFactory.getLogger(BusService.class);
    @Autowired
    Utils utils;
    @Autowired
    SeatLayoutService seatLayoutService;
    @Autowired
    BusRepo busRepo;
    @Autowired
    LocationRepo locationRepo;


    public Bus createBusWithoutStops(String type, String name, int totalSeats, List<String> days){
        Bus bus = new Bus();

        bus.setName(name);
        bus.setTotalSeats(totalSeats);
        bus.setDaysOfOperation(utils.getDaysOfOperationsFromInput(days));

        SeatLayout seatLayout = seatLayoutService.getSeatLayout(type, totalSeats);
        LOGGER.info("The seat layout size created: {}", seatLayout.getSeats().size());
        bus.setSeatLayout(seatLayout);
        bus = busRepo.save(bus);

        LOGGER.info("After saving: " + busRepo.findByBusId(bus.getBusId()).get().getSeatLayout().getSeats().size());
        return bus;
    }

    public Bus addStopsToBus(Long busId, List<String> locs, List<Time> times){
        Optional<Bus> busObject = busRepo.findByBusId(busId);
        if (busObject.isEmpty()){
            LOGGER.info("Cannot find any bus with id: " + busId);
            throw new IllegalArgumentException();
        }
        Bus bus = busObject.get();
        bus.setBusRoute(utils.getRouteFromEnteredLocations(bus.getBusId(), locs, times));
        bus = busRepo.save(bus);
        return bus;
    }

    public List<Bus> getBusesFromSrcToDest(String src, String dest){
        Location source = locationRepo.findByName(src).get();
        Location destination = locationRepo.findByName(dest).get();
        List<Bus> allBuses = busRepo.findAll();
        List<Bus> ret = new ArrayList<>();
        for (Bus busIt: allBuses){
            if (busIt.routeContains(source) && busIt.routeContains(destination)){
                ret.add(busIt);
            }
        }
        return ret;
    }

    public List<Seat> getAvaialableSeats(Long busId){
        LOGGER.info("Checking available seats for busId {}", busId);
        Bus bus = busRepo.findByBusId(busId).get();
        LOGGER.info("Bus Name corresponding to busId {} is {}", busId, bus.getName());

        SeatLayout seatLayout = bus.getSeatLayout();
        LOGGER.info("total seats in busId {} is {}", busId, seatLayout.getSeats().size());
        List<Seat> ret = new ArrayList<>(); //because seat number is the index itself for the bus (seat Id is unique for each and every seat but two buses can have same seat number
        for (int i=1; i<=seatLayout.getSeats().size(); i++){
            Seat seat = seatLayout.getSeats().get(i-1);
            if (!seat.getIsOccupied()){
                LOGGER.info("Seat available: " + i);
                ret.add(seat);
            }
        }
        return ret;
    }
}
