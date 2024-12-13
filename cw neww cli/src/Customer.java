public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (true) {
            //int batchSize= 2;
            ticketPool.removeTickets(2);


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

