import React, { useState } from "react";
import axios from "axios";

function ConfigurationForm({ addLog }) {
  const [config, setConfig] = useState({
    totalTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
    maxTicketCapacity: "",
  });

  const handleChange = (e) => {
    setConfig({ ...config, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:5000/config", config); // Replace with your backend URL
      addLog("Configuration updated successfully.");
    } catch (error) {
      addLog("Error updating configuration.");
      console.error("Error:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Configuration</h2>
      <input type="number" name="totalTickets" placeholder="Total Tickets" onChange={handleChange} required />
      <input type="number" name="ticketReleaseRate" placeholder="Release Rate (sec)" onChange={handleChange} required />
      <input type="number" name="customerRetrievalRate" placeholder="Retrieval Rate (sec)" onChange={handleChange} required />
      <input type="number" name="maxTicketCapacity" placeholder="Max Capacity" onChange={handleChange} required />
      <button type="submit">Update Configuration</button>
    </form>
  );
}

export default ConfigurationForm;
