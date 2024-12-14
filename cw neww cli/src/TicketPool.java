import java.util.LinkedList;
import java.util.Queue;
import java.math.BigDecimal;

public class TicketPool {
    private final int maximumCapacity;
    private final Queue<Ticket> tickets = new LinkedList<>();
    private int nextTicketId = 1; // Shared counter for unique ticket IDs


    public TicketPool(int maximumCapacity) {

        this.maximumCapacity = maximumCapacity;
    }

    // Method to initialize the pool with the specified number of tickets
    public synchronized void initializeTickets(int totalTickets) {
        for (int i = 0; i < totalTickets; i++) {
            Ticket ticket = new Ticket(i + 1, "Event", new BigDecimal("1000"));
            tickets.add(ticket);  // Directly add the ticket to the pool
        }

    }

    public synchronized void addTickets(int batchSize) {
        // If the pool is full, wait for a customer to buy tickets
        while (tickets.size() + batchSize > maximumCapacity) {

            try {
                System.out.println("Ticket pool full. Waiting for customers...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }


//        tickets.add(ticket);
        for (int i = 0; i < batchSize; i++) {
            Ticket ticket = new Ticket(nextTicketId++, "Event", new BigDecimal("1000"));
            tickets.add(ticket);
        }
        
        System.out.println(Thread.currentThread().getName() + " added " + batchSize + " tickets. Total tickets: " + tickets.size());


        // Notify customers that a ticket has been added
        notifyAll();
    }

    public synchronized void removeTickets(int batchSize) {
        while (tickets.size() < batchSize) {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting for vendors...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(Thread.currentThread().getName() + " bought " + batchSize + " tickets. Total available tickets: " + (tickets.size() - batchSize));
        System.out.print("Ticket details: ");
        for (int i = 0; i < batchSize; i++) {
            Ticket ticket = tickets.poll(); // Retrieve a ticket
            System.out.print(ticket);
            if (i < batchSize - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        notifyAll();
    }
}


