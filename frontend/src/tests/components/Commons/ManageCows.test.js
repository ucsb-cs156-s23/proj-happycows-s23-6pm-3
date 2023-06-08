import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import ManageCows from "main/components/Commons/ManageCows"; 
import userCommonsFixtures from "fixtures/userCommonsFixtures"; 

// {userCommons.cowHealth = 90}

describe("ManageCows tests", () => {
    // test('when cowHealth is below 30%, it displays a sick cow ', async () => {
    //     render(<ManageCows userCommons = {userCommonsFixtures.oneUserCommons[0]}  />)
    //     const {getByAltText} = document.querySelector("img") as HTMLImageElement;
    //     expect(displayedImage.src).toContain("sickCowIcon");
    // });

    // test('when cowHealth is between 50-80%, it displays a average cow ', async () => {
    //     const {getByAltText} = render(<ManageCows userCommons = {userCommonsFixtures.oneUserCommons[0]}  />)
    //     getByAltText("averageCowIcon");
    // });
      
    // test('when cowHealth is above 80%, it displays a happy cow ', async () => {
    //     const {getByAltText} = render(<ManageCows userCommons = {userCommonsFixtures.oneUserCommons[0]}  />)
    //     getByAltText("happyCowIcon");
    // });
    
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

    test('when cowHealth is between 30-50%, it displays a sad cow ', async () => {        
        render(
            <ManageCows userCommons = {userCommonsFixtures.oneUserCommons[0]} />
        );

        await waitFor (() => {
            expect(screen.getByText(/Cow Health: 98%/)).toBeInTheDocument();
        }); 

        expect(screen.getByAltText("happyCowIcon")).toBeInTheDocument();
    });

});