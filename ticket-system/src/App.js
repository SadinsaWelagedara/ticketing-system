import React, { useState } from 'react';
import './App.css';
import axios from 'axios';


function App() {
  const [totalTickets, setTotalTickets] = useState('');
  const [ticketReleaseRate, setTicketReleaseRate] = useState('');
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState('');
  const [maxTicketCapacity, setMaxTicketCapacity] = useState('');
  const [vendorCount, setVendorCount] = useState('');
  const [customerCount, setCustomerCount] = useState('');
  const [isRunning, setIsRunning] = useState(false);
  const [logs, setLogs] = useState([]);
  const [errors, setErrors] = useState({});

  

  const handleStart = () => {
    setIsRunning(true);
    setLogs([...logs, 'System started.']);
  };

  const handleStop = () => {
    setIsRunning(false);
    setLogs([...logs, 'System stopped.']);
  };

  const handleConfigSubmit = (e) => {
    e.preventDefault();
    if (!totalTickets || !ticketReleaseRate || !customerRetrievalRate || !maxTicketCapacity) {
      alert('Please fill all configuration fields.');
      return;
    }
    setLogs([...logs, 'Configuration submitted.']);
  };

  const handleVendorCustomerSubmit = (e) => {
    e.preventDefault();

    if (!vendorCount || !customerCount) {
      setErrors({
        vendorCount: !vendorCount ? 'Vendor count is required' : '',
        customerCount: !customerCount ? 'Customer count is required' : '',
      });
      return;
    }

    setErrors({});
    setLogs([...logs, 'Vendor and Customer configuration submitted.']);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>TICKETING SYSTEM</h1>
        <form onSubmit={handleConfigSubmit}>
          <div>
            <label>Total Tickets:</label>
            <input
              type="number"
              value={totalTickets}
              onChange={(e) => setTotalTickets(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Ticket Release Rate (seconds):</label>
            <input
              type="number"
              value={ticketReleaseRate}
              onChange={(e) => setTicketReleaseRate(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Customer Retrieval Rate (seconds):</label>
            <input
              type="number"
              value={customerRetrievalRate}
              onChange={(e) => setCustomerRetrievalRate(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Max Ticket Capacity:</label>
            <input
              type="number"
              value={maxTicketCapacity}
              onChange={(e) => setMaxTicketCapacity(e.target.value)}
              required
            />
          </div>
          <button type="submit">Submit Configuration</button>
        </form>

        <form onSubmit={handleVendorCustomerSubmit}>
          <div className="flex flex-col">
            <label
              htmlFor="vendorCount"
              className="text-sm font-medium text-gray-600 mb-2"
            >
              Vendor Count:
            </label>
            <input
              id="vendorCount"
              type="number"
              placeholder="Enter Vendor Count"
              className="border border-gray-300 rounded-md px-4 py-2 focus:ring-2 focus:ring-blue-400 focus:outline-none"
              onChange={(e) => setVendorCount(e.target.value)}
            />
            {errors.vendorCount && (
              <span className="text-red-500 text-sm">{errors.vendorCount}</span>
            )}
          </div>
          <div className="flex flex-col">
            <label
              htmlFor="customerCount"
              className="text-sm font-medium text-gray-600 mb-2"
            >
              Customer Count:
            </label>
            <input
              id="customerCount"
              type="number"
              placeholder="Enter Customer Count"
              className="border border-gray-300 rounded-md px-4 py-2 focus:ring-2 focus:ring-blue-400 focus:outline-none"
              onChange={(e) => setCustomerCount(e.target.value)}
            />
            {errors.customerCount && (
              <span className="text-red-500 text-sm">{errors.customerCount}</span>
            )}
          </div>
          <button type="submit">Submit</button>
        </form>

        <div>
          <h2>Control Panel</h2>
          <div className="controlButtons">
            <button onClick={handleStart} disabled={isRunning}>
              Start System
            </button>
            <button onClick={handleStop} disabled={!isRunning}>
              Stop System
            </button>
          </div>
        </div>
      </header>
    </div>
  );
}

export default App;



