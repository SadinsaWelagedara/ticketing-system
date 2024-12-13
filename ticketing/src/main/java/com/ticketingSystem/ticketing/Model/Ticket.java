package com.ticketingSystem.ticketing.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "tickets")
public class Ticket {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int ticketId;

        @Column(nullable = false)
        private String vendorId;

        private String customerId;

    public Ticket() {

    }


    // Getters and Setters
        public int getTicketId() {
            return ticketId;
        }

    public Ticket(String vendorId, String customerId) {
//        this.ticketId = ticketId;
        this.vendorId = vendorId;
        this.customerId = customerId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
