import React from 'react';

const VendorCount = ({ count }) => {
    return (
        <div className="vendor-count">
            <h3>Vendor Count</h3>
            <p>{count} Active Vendors</p>
        </div>
    );
};

export default VendorCount;
