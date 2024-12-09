import React from 'react';

const Logs = ({ logs }) => {
    return (
        <div className="logs">
            <h3>Transaction Logs</h3>
            <ul>
                {logs.map((log, index) => (
                    <li key={index}>{log}</li>
                ))}
            </ul>
        </div>
    );
};

export default Logs;
