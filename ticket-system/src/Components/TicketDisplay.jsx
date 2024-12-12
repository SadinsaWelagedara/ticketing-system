import React from "react";

function TicketDisplay({ tickets }) {
  return (
    <div>
      <h2>Ticket Availability</h2>
      {tickets.length > 0 ? (
        <ul>
          {tickets.map((ticket) => (
            <li key={ticket.ticketId}>{`Ticket ${ticket.ticketId}: ${ticket.eventName} - $${ticket.ticketPrice}`}</li>
          ))}
        </ul>
      ) : (
        <p>No tickets available.</p>
      )}
    </div>
  );
}

export default TicketDisplay;
