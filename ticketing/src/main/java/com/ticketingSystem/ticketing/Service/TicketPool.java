
package com.ticketingSystem.ticketing.Service;

import com.ticketingSystem.ticketing.Configuration.Configuration;
import com.ticketingSystem.ticketing.Model.Ticket;
import com.ticketingSystem.ticketing.Repository.TicketRepository;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class TicketPool {
    TicketRepository ticketRepository;
    //private final int maximumCapacity = 100; // Define the maximum capacity for the ticket pool
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();
    private final Queue<Ticket> tickets = new LinkedList<>();

    private volatile boolean running = false;

    private  int maximumCapacity;

    private Configuration configuration;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }


//    @Autowired
    //private Configuration config;
//    public TicketPoolService(Configuration config) {
//        this.maximumCapacity = config.getMaxTicketCapacity();
//    }

    public void setConfiguration(Configuration config){
        this.configuration = config;
    }




    //    private int vendorCount;
//    private int customerCount;
    List<Vendor> vendorList = new ArrayList<>();
    List<Customer> customerList = new ArrayList<>();
//    private int nextTicketId = 1; // Shared counter for unique ticket IDs

    public TicketPool() {

    }



    public String initializeUsers(int vendorCount, int customerCount) {
        for (int i = 0; i < vendorCount; i++) {
            String vendorId = "vendor-" + i;
            Vendor vendor = new Vendor(vendorId);
            vendorList.add(vendor);
        }
        for (int i = 0; i < customerCount; i++) {
            String customerId = "customer-" + i;
            Customer customer = new Customer(customerId);
            customerList.add(customer);
        }
        return "Vendors and Customers Initialized";
    }
    // Method to initialize the pool with a specified number of tickets
    public synchronized void initializeTickets(int totalTickets) {
        for (int i = 0; i < totalTickets && tickets.size() < maximumCapacity; i++) {
            Ticket ticket = new Ticket();
            // Assuming Ticket has a setVendorId method
            tickets.add(ticket);
        }
        System.out.println("Initialized ticket pool with " + tickets.size() + " tickets.");
    }

    // Start threads for vendors and customers
    public synchronized String startThreads() {
        if (running) {
            return "Threads are already running.";
        }

        running = true;


        // Start vendor threads
        for (int i = 0; i < configuration.getVendorCount(); i++) {
            int index = i; // To use inside the lambda
            Thread vendorThread = createAndStartThread(
                    () -> addTickets(configuration.getTicketReleaseRate(), vendorList.get(index % vendorList.size()).getVendorId()),
                    "Vendor-" + (i + 1));
            vendorThreads.add(vendorThread);
        }



        for (int i = 0; i < configuration.getCustomerCount(); i++) {
            Runnable consumer = new Customer((TicketPool)tickets, configuration.getCustomerRetrievalRate());
            Thread consumerThread = new Thread(consumer, "Consumer-" + (i + 1));
            consumerThread.start();
        }

        return "Threads started successfully.";
    }

    // Stop all threads
    public synchronized String stopThreads() {
        if (!running) {
            return "Threads are not running.";
        }

        running = false;

        // Interrupt and join vendor and customer threads
        interruptAndJoinThreads(vendorThreads);
        interruptAndJoinThreads(customerThreads);

        return "Threads stopped successfully.";
    }

    // Add tickets to the pool
    public synchronized void addTickets(int batchSize, String vendorId) {
        while (tickets.size() + batchSize > maximumCapacity) {
            try {
                System.out.println("Ticket pool full. Waiting for customers...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        for (int i = 0; i < batchSize; i++) {
            vendorId = vendorList.get(i % vendorList.size()).getVendorId();
            Ticket ticket = new Ticket();
            ticket.setVendorId(vendorId);
            tickets.add(ticket);
            ticketRepository.save(ticket);
        }

        System.out.println("Added " + batchSize + " tickets. Total tickets: " + tickets.size());
        notifyAll();
    }

    // Remove tickets from the pool
    public synchronized void removeTickets(int batchSize, String customerId) {
        while (tickets.size() < batchSize) {
            try {
                System.out.println("Not enough tickets. Waiting for vendors to add tickets...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Removing " + batchSize + " tickets. Tickets left after removal: " + (tickets.size() - batchSize));
        for (int i = 0; i < batchSize; i++) {
            String vendorId = customerList.get(i % customerList.size()).getCustomerId();
            Ticket ticket = new Ticket();
            ticket.setVendorId(vendorId);
            ticketRepository.updateCustomerId(customerId);
            ticket = tickets.poll(); // Retrieve and remove a ticket from the queue
            System.out.println("Ticket removed: " + ticket);
        }

        notifyAll();
    }

    // Create and start a thread
    private Thread createAndStartThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable, name);
        thread.start();
        return thread;
    }

    // Interrupt and join threads
    private void interruptAndJoinThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        }

        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        threads.clear();
    }

    @PreDestroy
    public void onShutdown() {
        // Stop threads when the application is shutting down
        stopThreads();
    }



//    //Get current ticket count
//    public synchronized int getTicketCount() {
//        return tickets.size();
//    }



}


