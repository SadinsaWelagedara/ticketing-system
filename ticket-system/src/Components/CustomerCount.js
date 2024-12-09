import React from 'react';

const CustomerCount = ({ count }) => {
    return (
        <div className="customer-count">
            <h3>Customer Count</h3>
            <p>{count} Active Customers</p>
        </div>
    );
};

export default CustomerCount;
