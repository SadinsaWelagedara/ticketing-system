import java.math.BigDecimal;

public class Ticket {
    private final int ticketId;
    private final String eventName;
    private final BigDecimal ticketPrice;

    public Ticket(int ticketId, String eventName, BigDecimal ticketPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}

