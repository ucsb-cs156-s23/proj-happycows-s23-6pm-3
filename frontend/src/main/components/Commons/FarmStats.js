import React from "react";
import { Card } from "react-bootstrap";
import Health from "./../../../assets/Health.png";
import Cash from "./../../../assets/Cash.png";
import Plot from "react-plotly.js";
const FarmStats = ({ userCommons, cowLots }) => {
    return (
        <Card>
            <Card.Header as="h5">Your Farm Stats</Card.Header>
            <Card.Body>
                {/* update total wealth and cow health with data from fixture */}
                <Card.Text>
                    <img className="icon" src={Cash} alt="Cash"></img>
                </Card.Text>
                <Card.Text>
                    Total Wealth: ${userCommons.totalWealth.toFixed(2)}
                </Card.Text>
                <Card.Text>
                    <img className="icon" src={Health} alt="Health"></img>
                </Card.Text>
                <Plot
                    data={[
                        {
                            x: cowLots[0],
                            y: cowLots[1],
                            type: "bar",
                            width: 4,
                        },
                    ]}
                    layout={{
                        width: 400,
                        height: 300,
                        showlegend: false,
                        title: "Cow Health",
                        xaxis: {
                            range: [100, 0],
                            tickmode: "linear",
                            dtick: 100,
                        },
                        yaxis: {
                            dtick: 10,
                        },
                    }}
                    config={{
                        displayModeBar: false,
                    }}
                />
            </Card.Body>
        </Card>
    );
};

export default FarmStats;
