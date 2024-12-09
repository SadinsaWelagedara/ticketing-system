/*import React from 'react';

const Configuration = ({ config }) => {
    return (
        <div className="configuration">
            <h3>System Configuration</h3>
            <ul>
                <li>Total Tickets: {config.totalTickets}</li>
                <li>Ticket Release Rate: {config.ticketReleaseRate} seconds</li>
                <li>Customer Retrieval Rate: {config.customerRetrievalRate} seconds</li>
                <li>Max Ticket Capacity: {config.maxTicketCapacity}</li>
            </ul>
        </div>
    );
};

export default Configuration;*/

import React, { useState } from 'react';
import axios from 'axios';

function Configuration() {
  const [config, setConfig] = useState({
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
  });

  const fetchConfig = () => {
    axios.get('/api/config').then(response => {
      setConfig(response.data);
    });
  };

  return (
    <div className="configuration">
      <h2>Configuration</h2>
      <button onClick={fetchConfig}>Load Configuration</button>
      <ul>
        <li>Total Tickets: {config.totalTickets}</li>
        <li>Ticket Release Rate: {config.ticketReleaseRate} sec</li>
        <li>Customer Retrieval Rate: {config.customerRetrievalRate} sec</li>
        <li>Max Ticket Capacity: {config.maxTicketCapacity}</li>
      </ul>
    </div>
  );
}

export default Configuration;


