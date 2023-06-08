import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import ManageCows from "main/components/Commons/ManageCows"; 
import userCommonsFixtures from "fixtures/userCommonsFixtures"; 

describe("ManageCows tests", () => {    

    test("renders without crashing", () => {
        render(
            <ManageCows userCommons = {userCommonsFixtures.oneUserCommons[0]} onBuy={(userCommons) => { console.log("onBuy called:",userCommons); }} onSell={ (userCommons) => { console.log("onSell called:",userCommons); }} />
        );
    });

    test("buying and selling a cow", async () => {
        const mockBuy = jest.fn();
        const mockSell = jest.fn();

        render(
            <ManageCows userCommons = {userCommonsFixtures.oneUserCommons[0]} onBuy={mockBuy} onSell={mockSell} />
        );

        const buyButton = screen.getByTestId("buy-cow-button");
        const sellButton = screen.getByTestId("sell-cow-button");
        
        fireEvent.click(buyButton);
        await waitFor( ()=>expect(mockBuy).toHaveBeenCalled() );

        fireEvent.click(sellButton);
        await waitFor( ()=>expect(mockSell).toHaveBeenCalled() );
        
    });

    test("when cowHealth is 93% or above, it displays a happy cow", async () => {        
        render(
            <ManageCows userCommons = {userCommonsFixtures.threeUserCommons[0]} />
        );

        await waitFor (() => {
            expect(screen.getByText(/Cow Health: 93%/)).toBeInTheDocument();
        }); 

        expect(screen.getByAltText("happyCowIcon")).toBeInTheDocument();
    });
    
    test("when cowHealth is 84%-92%, it displays a average cow", async () => {        
        render(
            <ManageCows userCommons = {userCommonsFixtures.fiveUserCommons[3]} />
        );

        await waitFor (() => {
            expect(screen.getByText(/Cow Health: 84%/)).toBeInTheDocument();
        }); 

        expect(screen.getByAltText("averageCowIcon")).toBeInTheDocument();
    });

    test("when cowHealth is 72%-83%, it displays a sad cow", async () => {        
        render(
            <ManageCows userCommons = {userCommonsFixtures.tenUserCommons[9]} />
        );

        await waitFor (() => {
            expect(screen.getByText(/Cow Health: 72%/)).toBeInTheDocument();
        }); 

        expect(screen.getByAltText("sadCowIcon")).toBeInTheDocument();
    });

    test("when cowHealth is below 72%, it displays a sick cow", async () => {        
        render(
            <ManageCows userCommons = {userCommonsFixtures.threeUserCommons[2]} />
        );

        await waitFor (() => {
            expect(screen.getByText(/Cow Health: 2%/)).toBeInTheDocument();
        }); 

        expect(screen.getByAltText("sickCowIcon")).toBeInTheDocument();
    });

});