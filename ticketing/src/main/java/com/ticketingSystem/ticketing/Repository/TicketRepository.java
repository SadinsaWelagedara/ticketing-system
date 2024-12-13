package com.ticketingSystem.ticketing.Repository;



import com.ticketingSystem.ticketing.Model.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Modifying
    @Transactional
    @Query ("update Ticket t set t.customerId = :newCustomerId where t.customerId is null ")
    void updateCustomerId(@Param("newCustomerId") String newCustomerId);
}


