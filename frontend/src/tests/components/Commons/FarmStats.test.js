import { render, screen, waitFor } from "@testing-library/react";
import FarmStats from "main/components/Commons/FarmStats";
import userCommonsFixtures from "fixtures/userCommonsFixtures";

jest.mock("react-plotly.js", () => ({
    __esModule: true,
    default: () => <div data-testid="plotly-mock"></div>,
}));

describe("FarmStats tests", () => {
    test("renders without crashing", () => {
        render(
            <FarmStats
                userCommons={userCommonsFixtures.oneUserCommons[0]}
                cowLots={[[], []]}
            />
        );
    });

    test("contains correct content", async () => {
        render(
            <FarmStats
                userCommons={userCommonsFixtures.oneUserCommons[0]}
                cowLots={[[], []]}
            />
        );

        await waitFor(() => {
            expect(
                screen.getByText(/Total Wealth: \$1000/)
            ).toBeInTheDocument();
        });
    });
});
