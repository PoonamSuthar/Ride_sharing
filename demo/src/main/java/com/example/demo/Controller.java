package com.example.demo;

import com.example.demo.Classes.Bus;
import com.example.demo.Classes.CustomUser;
import com.example.demo.Classes.Seat;
import com.example.demo.Repositories.BusRepo;
import com.example.demo.Repositories.UserRepo;
import com.example.demo.Service.BusService;
import com.example.demo.Service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {
    private final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    UserRepo userRepo;
    @Autowired
    BusService busService;

    @Autowired
    BusRepo busRepo;

    @Autowired
    SeatService seatService;

    @GetMapping("/createBus")
    public String getCreateBusPage(Model model){
        return "createBus";
    }

    @PostMapping("/addStops")
    public String getAddStopsPage(@RequestParam("name") String name,
            @RequestParam("seats") int seats,
            @RequestParam("numStops") int numStops,
            @RequestParam("daysOfOperation") List<String> daysOfOperation,
            Model model) {
        Bus bus = busService.createBusWithoutStops("Normal", name, seats, daysOfOperation);
        bus = busRepo.save(bus);
        model.addAttribute("busId", bus.getBusId());
        LOGGER.info("busId in addStops post: " + bus.getBusId());
        model.addAttribute("numStops", numStops);
        List<String> locations = new ArrayList<>();
        model.addAttribute("locations", locations);
        return "addStops";
    }

    @GetMapping("/addStops")
    public String getAddStopsPage(@RequestParam("numStops") int numStops, Model model) {
        LOGGER.info("busId in addStops Get: " + (Long) model.getAttribute("busId"));
        model.addAttribute("numStops", numStops);
        model.addAttribute("busId", (Long) model.getAttribute("busId"));
        return "addStops";
    }

    @PostMapping("/saveStops")
    public String saveStops(@RequestParam("busId") Long busId,
            @RequestParam("locations") List<String> locations,
            @RequestParam("times") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) List<LocalTime> times,
            Model model) {
        // Ensure the sizes of locations and times are the same
//        List<String> locations = (List<String>) model.getAttribute("locations");

        LOGGER.info("Recvd busId at saveStop" +  busId.toString());

        if (locations.size() != times.size()) {
            throw new IllegalArgumentException("The number of locations and times must be the same");
        }

        List<Time> sqlTimes = new ArrayList<>();
        // Process the locations and times
        for (int i = 0; i < locations.size(); i++) {
            LocalTime time = times.get(i);
            sqlTimes.add(Time.valueOf(time));
            LOGGER.info("Recvd: Location: " + locations.get(i) + ", Time: " + sqlTimes.get(i));
        }

//        Long busId = (Long) model.getAttribute("busId");
        Bus bus = busService.addStopsToBus(busId, locations, sqlTimes);

        // Redirect or return view name after processing
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String getSuccess(Model model){
        return "redirect:/createBus";
    }

    @GetMapping("/browse")
    public String getBrowsePage(Model model){
        return "browse";
    }

    @PostMapping("/browse/response")
    public void getBrowsePage(@RequestParam("src") String src,
            @RequestParam("dest") String dest,
            Model model){
        List<Bus> busesList = busService.getBusesFromSrcToDest(src, dest);
        LOGGER.info("browse count post: " + busesList.size());
    }

    @GetMapping("/browse/response")
    public String getBrowseResponse(@RequestParam("src") String src,
            @RequestParam("dest") String dest,
            Model model){
        LOGGER.info("get browseresponse");
        List<Bus> busesList = busService.getBusesFromSrcToDest(src, dest);
        LOGGER.info("browse count get: " + busesList.size());

        model.addAttribute("busesList", busesList);
        model.addAttribute("src", src);
        model.addAttribute("dest", dest);
        return "browseResponse";
    }


    @GetMapping("/availableSeats/{busId}")
    public String getAvailableSeatNumbers(@PathVariable Long busId, Model model) {
        // Add logic to handle seat booking here, for now, we just log the busId
        LOGGER.info("Booking seat for busId: " + busId);
        List<Seat> availableSeats = busService.getAvaialableSeats(busId);
        LOGGER.info("available seats: " + availableSeats.size());
        // Add attributes to model if needed for the booking page
        model.addAttribute("seatList", availableSeats);

        // Return a view name for booking confirmation or booking details
        return "availableSeats"; // Assume "bookingPage" is the Thymeleaf template for booking
    }

    @GetMapping("/bookSeat/{seatId}")
    public String bookSeat(@PathVariable Long seatId, Model model) {
        // Add logic to handle seat booking here, for now, we just log the busId
        LOGGER.info("Booking seat for seatId: " + seatId);
        //book seat
        if (seatService.bookSeat(seatId)){
            return "Success";
        }
        return "Failure";
    }

    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @PostMapping("/login")
    public void postLogin(){

    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new CustomUser());
        return "register";
    }

    @PostMapping("/register/save")
    public String registerUser(@ModelAttribute CustomUser user) {
        // Logic to save the user, e.g., userService.save(user);
        // For now, just log the user details
        LOGGER.info("reggister/save visited");

        Optional<CustomUser> checkExisting = userRepo.findByUsername(user.getUsername());
        if (checkExisting.isPresent()){
            return "Failure";
        }
        else {
            LOGGER.info("REGISTERED: " + user.toString());
            user = userRepo.save(user);
        }
        System.out.println("User registered: " + user);
        return "redirect:/login";
    }
}
