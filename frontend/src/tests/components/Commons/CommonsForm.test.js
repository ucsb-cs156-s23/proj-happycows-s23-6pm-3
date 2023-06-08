import { fireEvent, render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import CommonsForm from "main/components/Commons/CommonsForm";

const mockedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: () => mockedNavigate,
}));

describe("CommonsForm tests", () => {
    it("renders correctly", async () => {
        render(
            <Router>
                <CommonsForm />
            </Router>
        );

        expect(await screen.findByText(/Commons Name/)).toBeInTheDocument();

        [
            /Starting Balance/,
            /Cow Price/,
            /Milk Price/,
            /Starting Date/,
            /Last Date/,
            /Degradation Rate/,
            /Carrying Capacity/,
            /Show Leaderboard\?/,
        ].forEach((pattern) => {
            expect(screen.getByText(pattern)).toBeInTheDocument();
        });
        expect(screen.getByText(/Create/)).toBeInTheDocument();
    });

    it("has validation errors for required fields", async () => {
        const submitAction = jest.fn();

        render(
            <Router>
                <CommonsForm submitAction={submitAction} buttonLabel="Create" />
            </Router>
        );

        // Input empty values to the form to test error message
        const startingBalanceInput = screen.getByTestId(
            "CommonsForm-startingBalance"
        );
        fireEvent.change(startingBalanceInput, { target: { value: "" } });
        const cowPriceInput = screen.getByTestId("CommonsForm-cowPrice");
        fireEvent.change(cowPriceInput, { target: { value: "" } });
        const milkPriceInput = screen.getByTestId("CommonsForm-milkPrice");
        fireEvent.change(milkPriceInput, { target: { value: "" } });
        const startingDateInput = screen.getByTestId(
            "CommonsForm-startingDate"
        );
        fireEvent.change(startingDateInput, { target: { value: "" } });
        const lastDateInput = screen.getByTestId(
            "CommonsForm-lastDate"
        );
        fireEvent.change(lastDateInput, { target: { value: "" } });
        const degradationRateInput = screen.getByTestId(
            "CommonsForm-degradationRate"
        );
        fireEvent.change(degradationRateInput, { target: { value: "" } });
        const carryingCapacityInput = screen.getByTestId(
            "CommonsForm-carryingCapacity"
        );
        fireEvent.change(carryingCapacityInput, { target: { value: "" } });
        const priceChangeInput = screen.getByTestId("CommonsForm-priceChange");
        fireEvent.change(priceChangeInput, { target: { value: "" } });

        //Check error message
        expect(
            await screen.findByTestId("CommonsForm-name")
        ).toBeInTheDocument();
        const submitButton = screen.getByTestId("CommonsForm-Submit-Button");
        expect(submitButton).toBeInTheDocument();

        fireEvent.click(submitButton);
        expect(
            await screen.findByText(/commons name is required/i)
        ).toBeInTheDocument();

        expect(
            screen.getByText(/starting balance is required/i)
        ).toBeInTheDocument();
        expect(screen.getByText(/cow price is required/i)).toBeInTheDocument();
        expect(screen.getByText(/milk price is required/i)).toBeInTheDocument();
        expect(
            screen.getByText(/starting date is required/i)
        ).toBeInTheDocument();
        expect(
            screen.getByText(/last date is required/i)
        ).toBeInTheDocument();
        expect(
            screen.getByText(/degradation rate is required/i)
        ).toBeInTheDocument();
        expect(
            screen.getByText(/Carrying capacity is required/i)
        ).toBeInTheDocument();
        expect(
            screen.getByText(/Cow price change is required/i)
        ).toBeInTheDocument();

        expect(submitAction).not.toBeCalled();
    });
});
