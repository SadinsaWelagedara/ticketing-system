public class Vendor implements Runnable {
    private final int releaseRate; // Rate of release in seconds
    private final TicketPool ticketPool;

    public Vendor(int releaseRate, TicketPool ticketPool) {
        this.releaseRate = releaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {

        while (true) {
             // Number of tickets to add in a batch
            ticketPool.addTickets(4);
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


