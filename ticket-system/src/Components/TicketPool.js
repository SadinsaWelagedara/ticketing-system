import React from 'react';

const TicketPool = ({ availableTickets }) => {
    return (
        <div className="ticket-pool">
            <h3>Ticket Pool Status</h3>
            <p>Total Available Tickets: {availableTickets}</p>
        </div>
    );
};

export default TicketPool;
