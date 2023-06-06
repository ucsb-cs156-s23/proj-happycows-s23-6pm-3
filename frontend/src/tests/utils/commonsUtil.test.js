import { cellToAxiosParamsDelete, onDeleteSuccess, daysElapsed } from "main/utils/commonsUtils";
import mockConsole from "jest-mock-console";

const mockToast = jest.fn();
jest.mock('react-toastify', () => {
    const originalModule = jest.requireActual('react-toastify');
    return {
        __esModule: true,
        ...originalModule,
        toast: (x) => mockToast(x)
    };
});

describe("CommonsUtils", () => {
    describe("onDeleteSuccess", () => {
        test("It puts the message on console.log and in a toast", () => {
            // arrange
            const restoreConsole = mockConsole();

            // act
            onDeleteSuccess("abc");
            
            // assert
            expect(mockToast).toHaveBeenCalledWith("abc");
            expect(console.log).toHaveBeenCalled();
            const message = console.log.mock.calls[0][0];
            expect(message).toMatch("abc");

            restoreConsole();
        });

    });
    describe("cellToAxiosParamsDelete", () => {
        test("It returns the correct params", () => {
            // arrange
            const cell = { row: { values: { "commons.id" : 17 } } };

            // act
            const result = cellToAxiosParamsDelete(cell);

            // assert
            expect(result).toEqual({
                url: "/api/commons",
                method: "DELETE",
                params: { id: 17 }
            });
        });
    });

    describe("daysElapsed test", () => {
        test("Difference between 2 dates", () => {
           var startingDate = "2023-05-05T00:00:00";
           var lastDate = "2023-05-09T21:40:00";
          expect(daysElapsed(startingDate,lastDate)).toBe(5);
        });
    
        test("Difference between 2 times on same day", () => {
           var startingDate = "2023-05-05T00:00:00";
           var lastDate = "2023-05-05T21:40:00";
          expect(daysElapsed(startingDate,lastDate)).toBe(1);
        });
    
        test("Negative days checker", () => {
           var startingDate = "2023-05-05T00:30:00";
           var lastDate = "2023-05-04T21:40:00";
          expect(daysElapsed(startingDate,lastDate)).toBe(0);
        });
    
      });
});
