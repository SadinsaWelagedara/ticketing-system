import React from "react";

function ControlPanel({ isRunning, setIsRunning, addLog }) {
  const handleStart = () => {
    setIsRunning(true);
    addLog("System started.");
  };

  const handleStop = () => {
    setIsRunning(false);
    addLog("System stopped.");
  };

  return (
    <div>
      <h2>Control Panel</h2>
      <button onClick={handleStart} disabled={isRunning}>
        Start
      </button>
      <button onClick={handleStop} disabled={!isRunning}>
        Stop
      </button>
    </div>
  );
}

export default ControlPanel;
