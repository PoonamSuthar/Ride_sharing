package com.example.demo.Service;

import com.example.demo.Classes.DaysOfWeek;
import com.example.demo.Classes.Location;
import com.example.demo.Classes.StopLocation;
import com.example.demo.Repositories.BusRepo;
import com.example.demo.Repositories.DaysOfWeekRepo;
import com.example.demo.Repositories.LocationRepo;
import com.example.demo.Repositories.StopLocationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Utils {
    private final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    @Autowired
    DaysOfWeekRepo daysOfWeekRepo;
    @Autowired
    LocationRepo locationRepo;
    @Autowired
    StopLocationRepo stopLocationRepo;
    @Autowired
    BusRepo busRepo;
    public List<DaysOfWeek> getDaysOfOperationsFromInput(List<String> input){
        List<DaysOfWeek> ret = new ArrayList<>();
        for (String inp: input){
            //inp should be MON,TUE... etc
            LOGGER.info("Recv req for adding day to Bus: " + inp);
            DaysOfWeek day = daysOfWeekRepo.findByDay(inp);
            ret.add(day);
        }
        return ret;
    }

    public List<StopLocation> getRouteFromEnteredLocations(Long busId, List<String> input, List<Time> etas){
        List<StopLocation> ret = new ArrayList<>();
        for (int i=0; i<input.size(); i++){
            String inp = input.get(i);
            Time eta = etas.get(i);
            Optional<Location> loc = locationRepo.findByName(inp);
            Location newLocation;
            if (loc.isEmpty()){
                newLocation = new Location();
                newLocation.setName(inp);
                newLocation = locationRepo.save(newLocation);
            }
            else {
                newLocation = loc.get();
            }
            StopLocation stopLocation = new StopLocation();
            stopLocation.setLocationForeign(newLocation);
            stopLocation.setEta(eta);
            stopLocation.setBusForeign(busRepo.findByBusId(busId).get());
            stopLocationRepo.save(stopLocation);
            ret.add(stopLocation);
        }
        return ret;
    }
}
