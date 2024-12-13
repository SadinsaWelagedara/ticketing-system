package com.ticketingSystem.ticketing.Service;

public class Customer implements Runnable {
    private  TicketPool ticketPool;
    private  int retrievalRate;
    private String customerId;

    public Customer(String customerId) {
        this.customerId = customerId;
    }

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public int getRetrievalRate() {
        return retrievalRate;
    }

    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void run() {
        while (true) {
            //int batchSize= 2;
            ticketPool.removeTickets(2, customerId);

            try {
                Thread.sleep(retrievalRate * 1000L);
            } catch (InterruptedException e) {
                //System.out.println(Thread.currentThread().getName() + " interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }


}

