import React from "react";
import OurTable from "main/components/OurTable";

export default function BalanceTable({ balance }) {
    
    // Stryker disable ArrayDeclaration : [columns] and [students] are performance optimization; mutation preserves correctness
    const memoizedColumns = React.useMemo(() => 
        [
            {
                Header: "Total Wealth",
                accessor: (row) => `$${row.totalWealth.toFixed(2)}`,
            },
            {
                Header: "Date",
                accessor: "date",
            },
            {
                Header: "NumCows",
                accessor: "numCows",
            },
        ], 
    []);
    const memoizedDates = React.useMemo(() => balance, [balance]);
    // Stryker enable ArrayDeclaration

    return <OurTable
        data={memoizedDates}
        columns={memoizedColumns}
        testid={"BalanceTable"}
    />;
};