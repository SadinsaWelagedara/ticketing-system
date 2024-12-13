package com.ticketingSystem.ticketing.Service;

public class Vendor implements Runnable {
    private  int releaseRate; // Rate of release in seconds
    private  TicketPool ticketPool;
    private  String vendorId;

    public int getReleaseRate() {
        return releaseRate;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Vendor(String vendorId) {
        this.vendorId = vendorId;
    }

    public Vendor(int releaseRate, TicketPool ticketPool, String vendorId) {
        this.releaseRate = releaseRate;
        this.ticketPool = ticketPool;
        this.vendorId = vendorId;
    }


    @Override
    public void run() {
        //int ticketId = 1; // Initialize ticket ID
        while (true) {
            // Number of tickets to add in a batch
            ticketPool.addTickets(4,vendorId);
            //ticketId += 4;// Increment the ticket ID for the next batch


            //System.out.println(Thread.currentThread().getName() + " released 4 tickets.");

            try {
                Thread.sleep(releaseRate * 1000L); // Wait for the release rate duration
            } catch (InterruptedException e) {
                //System.out.println(Thread.currentThread().getName() + " interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }


}
