import { padWithZero, timestampToDate } from "main/utils/dateUtils";


describe("dateUtils tests", () => {

  describe("padWithZero tests", () => {

    test("pads when less than 10", () => {
      expect(padWithZero(0)).toBe("00");
      expect(padWithZero(1)).toBe("01");
      expect(padWithZero(9)).toBe("09");

    });

    test("does not pad with 10 or greater", () => {
      expect(padWithZero(10)).toBe(10);
      expect(padWithZero(11)).toBe(11);
    });

  });

  describe("timestampToDate tests", () => {
    it("converts properly", () => {
      expect(timestampToDate(1653346250816)).toBe("2022-05-23");
    });
  });

  describe("daysElapsed test", () => {
    test("Difference between 2 dates", () => {
      const startingDate = "2023-05-05T00:00:00";
      const lastDate = "2023-05-09T21:40:00";
      expect(calculateDays(startingDate,lastDate)).toBe(4);
    });

    test("Difference between 2 times on same day", () => {
      const startingDate = "2023-05-05T00:00:00";
      const lastDate = "2023-05-05T21:40:00";
      expect(calculateDays(startingDate,lastDate)).toBe(0);
    });

    test("Negative days checker", () => {
      const startingDate = "2023-05-05T00:30:00";
      const lastDate = "2023-05-04T21:40:00";
      expect(calculateDays(startingDate,lastDate)).toBe(0);
    });
    
  });

});
