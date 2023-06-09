import { render, screen, fireEvent } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router-dom";
import CommonsTable from "main/components/Commons/CommonsTable";
import { currentUserFixtures } from "fixtures/currentUserFixtures";
import commonsPlusFixtures from "fixtures/commonsPlusFixtures";

const mockedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: () => mockedNavigate,
}));

describe("UserTable tests", () => {
    const queryClient = new QueryClient();

    test("renders without crashing for empty table with user not logged in", () => {
        const currentUser = null;

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CommonsTable commons={[]} currentUser={currentUser} />
                </MemoryRouter>
            </QueryClientProvider>
        );
    });
    test("renders without crashing for empty table for ordinary user", () => {
        const currentUser = currentUserFixtures.userOnly;

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CommonsTable commons={[]} currentUser={currentUser} />
                </MemoryRouter>
            </QueryClientProvider>
        );
    });

    test("renders without crashing for empty table for admin", () => {
        const currentUser = currentUserFixtures.adminUser;

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CommonsTable commons={[]} currentUser={currentUser} />
                </MemoryRouter>
            </QueryClientProvider>
        );
    });

    test("Has the expected column headers and content for adminUser", () => {
        const currentUser = currentUserFixtures.adminUser;

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CommonsTable
                        commons={commonsPlusFixtures.threeCommonsPlus}
                        currentUser={currentUser}
                    />
                </MemoryRouter>
            </QueryClientProvider>
        );

        const expectedHeaders = [
            "id",
            "Name",
            "Cow Price",
            "Milk Price",
            "Starting Balance",
            "Starting Date",
            "Degradation Rate",
            "Carrying Capacity",
            "Show Leaderboard?",
        ];
        const expectedFields = [
            "id",
            "name",
            "cowPrice",
            "milkPrice",
            "startingBalance",
            "startingDate",
            "degradationRate",
            "carryingCapacity",
            "showLeaderboard",
        ];
        const expectedFieldContents = [
            3,
            "Not DLG",
            11.0,
            4.0,
            74.0,
            "2022-11-26",
            5.0,
            123,
            false,
        ];
        const testId = "CommonsTable";

        expectedHeaders.forEach((headerText) => {
            const header = screen.getByText(headerText);
            expect(header).toBeInTheDocument();
        });

        expectedFields.forEach((field) => {
            const header = screen.getByTestId(
                `${testId}-cell-row-0-col-commons.${field}`
            );
            expect(header).toBeInTheDocument();
        });

        expectedFieldContents.forEach((contentText) => {
            const content = screen.getByText(contentText);
            expect(content).toBeInTheDocument();
        });

        expect(
            screen.getByTestId(`${testId}-cell-row-0-col-commons.id`)
        ).toHaveTextContent("1");
        expect(
            screen.getByTestId(`${testId}-cell-row-1-col-commons.id`)
        ).toHaveTextContent("2");
    });

    test("Has the expected column headers and content for adminUser commonsPlus", () => {
        const currentUser = currentUserFixtures.adminUser;

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CommonsTable
                        commons={commonsPlusFixtures.threeCommonsPlus}
                        currentUser={currentUser}
                    />
                </MemoryRouter>
            </QueryClientProvider>
        );
        const testId = "CommonsTable";

        const expectedHeaders = ["Cows", "Price Change"];

        const expectedFields = ["totalCows", "priceChange"];

        const expectedFieldContents = [90, 0.2];

        expectedHeaders.forEach((headerText) => {
            const header = screen.getByText(headerText);
            expect(header).toBeInTheDocument();
        });

        expectedFields.forEach((field) => {
            const header = screen.getByTestId(
                `${testId}-cell-row-0-col-${field}`
            );
            expect(header).toBeInTheDocument();
        });

        expectedFieldContents.forEach((contentText) => {
            const content = screen.getByText(contentText);
            expect(content).toBeInTheDocument();
        });
    });

    test("Has the correct buttons", () => {
        const currentUser = currentUserFixtures.adminUser;

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CommonsTable
                        commons={commonsPlusFixtures.threeCommonsPlus}
                        currentUser={currentUser}
                    />
                </MemoryRouter>
            </QueryClientProvider>
        );

        const editButton = screen.getByTestId(
            "CommonsTable-cell-row-0-col-Edit-button"
        );
        expect(editButton).toHaveClass("btn-primary");

        const deleteButton = screen.getByTestId(
            "CommonsTable-cell-row-0-col-Delete-button"
        );
        expect(deleteButton).toHaveClass("btn-danger");

        const leaderboardButton = screen.getByTestId(
            "CommonsTable-cell-row-0-col-Leaderboard-button"
        );
        expect(leaderboardButton).toHaveClass("btn-secondary");
    });

    test("logs delete callback", () => {
        const currentUser = currentUserFixtures.adminUser;
        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CommonsTable
                        commons={commonsPlusFixtures.oneCommonsPlus}
                        currentUser={currentUser}
                    />
                </MemoryRouter>
            </QueryClientProvider>
        );

        const deleteButton = screen.getByTestId(
            "CommonsTable-cell-row-0-col-Delete-button"
        );
        console.log = jest.fn(); // Create a mock function for console.log
        fireEvent.click(deleteButton);
        expect(console.log).toHaveBeenCalledWith(
            "deleteCallback cell=",
            expect.anything()
        );
    });
});
