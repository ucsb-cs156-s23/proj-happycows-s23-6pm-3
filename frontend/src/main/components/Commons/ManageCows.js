import React from "react";
import { Card, Button, Row, Col } from "react-bootstrap";
import happyCowHead from "./../../../assets/happycow.png";
import averageCowHead from "./../../../assets/averagecow.png";
import sadCowHead from "./../../../assets/sadcow.png";
import sickCowHead from "./../../../assets/sickcow.png";
//import cowHead from "./../../../assets/happycow.png";

// add parameters 
const ManageCows = ({userCommons,commons, onBuy, onSell}) =>  {
    // update cowPrice from fixture
    var cowHealth = Math.round(userCommons.cowHealth*100)/100;
    var cowHead = averageCowHead;
    var cowIcon = "happyCowIcon";

    if (cowHealth >= 0) {
        cowHead = sickCowHead;
        cowIcon = "sickCowIcon";
    }
    if (cowHealth >= 30) {
        cowHead = sadCowHead;
        cowIcon = "sadCowIcon";
    }
    if (cowHealth >= 50) {
        cowHead = averageCowHead;
        cowIcon = "averageCowIcon";
    }
    if (cowHealth >= 80) {
        cowHead = happyCowHead;
        cowIcon = "happyCowIcon";
    }

    return (
        <Card>
        <Card.Header as="h5">Manage Cows</Card.Header>
        <Card.Body>
            {/* change $10 to info from fixture */}
            <Card.Title>Market Cow Price: ${commons?.cowPrice}</Card.Title>
            <Card.Title>Number of Cows: {userCommons.numOfCows}</Card.Title>
            <Card.Title>Current Milk Price: ${commons?.milkPrice} </Card.Title>
                <Row>
                    <Col>
                        <Card.Text>
                            <img alt={cowIcon} className="icon" src={cowHead}></img>
                            <img alt={cowIcon} className="icon" src={cowHead}></img>
                        </Card.Text>
                    </Col>
                    <Col>
                        <Button variant="outline-danger" onClick={()=>{onBuy(userCommons)}} data-testid={"buy-cow-button"}>Buy cow</Button>
                        <br/>
                        <br/>
                        <Button variant="outline-danger" onClick={()=>{onSell(userCommons)}} data-testid={"sell-cow-button"}>Sell cow</Button>
                        <br/>
                        <br/>
                    </Col>
                </Row>
                    Note: Buying cows buys at current cow price, but selling cows sells at current cow price
                    times the average health of cows as a percentage! 
        </Card.Body>
        </Card>
    ); 
}; 

export default ManageCows; 