package com.ticketingSystem.ticketing.Controller;

import com.ticketingSystem.ticketing.Configuration.Configuration;
import com.ticketingSystem.ticketing.Service.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticketingSystem")
@CrossOrigin(origins="*")
public class TicketController {
    Configuration config;

    @Autowired
    private TicketPool ticketPool;

    @PostMapping("/config")
    public String setConfig(@RequestBody Configuration config) {
        try {
            ticketPool.setConfiguration(config);
        } catch (Exception e) {
            e.getMessage();
            return e.getMessage();
        }
        return "Configurations successfully updated";
    }


    @PostMapping("/initialize")
    public String initializeUsers(@RequestParam int vendors, @RequestParam int customers) {
        return ticketPool.initializeUsers(vendors, customers);

    }

    @PostMapping("/start")
    public String startThreads() {
        return ticketPool.startThreads();

    }

    @PostMapping("/stop")
    public String stopThreads() {
        return ticketPool.stopThreads();
    }

}


