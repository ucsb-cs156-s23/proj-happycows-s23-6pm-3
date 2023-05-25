import React from "react";
import { Card } from "react-bootstrap";
import Health from "./../../../assets/Health.png";
import Cash from "./../../../assets/Cash.png";
import BalanceTable from "main/components/Commons/BalanceTable";
import { timestampToDate } from "main/utils/dateUtils";

const FarmStats = ({userCommons, balance}) => {
    const balanceForTable =
        balance ?
        balance.map(balance => ({
            date: timestampToDate(balance.timestamp),
            ...balance
        })) : 
        // Stryker disable next-line ArrayDeclaration : no need to test what happens if [] is replaced with ["Stryker was here"]
        [];
    return (
        <Card>
        <Card.Header as="h5">Your Farm Stats</Card.Header>
        <Card.Body>
            {/* update total wealth and cow health with data from fixture */}
            <Card.Text>
                <img className="icon" src={Cash} alt="Cash"></img>
            </Card.Text>
            <Card.Text>
                Total Wealth: ${userCommons.totalWealth}
            </Card.Text>
            <Card.Text>
                <img className="icon" src={Health} alt="Health"></img> 
            </Card.Text>
            <Card.Text>
                Cow Health: {Math.round(userCommons.cowHealth*100)/100}%
            </Card.Text>
            <BalanceTable balance={balanceForTable} />
        </Card.Body>
        </Card>
    ); 
}; 

export default FarmStats; 