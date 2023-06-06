package edu.ucsb.cs156.happiercows.entities;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.ucsb.cs156.happiercows.entities.User;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDateTime;

public class UserCommonsTests {
    LocalDateTime start = LocalDateTime.parse("2020-01-21T06:47:22.756");
    LocalDateTime start2 = LocalDateTime.parse("2020-03-05T15:50:10");
    LocalDateTime end = LocalDateTime.parse("3000-01-21T06:47:22.756");
    LocalDateTime end2 = LocalDateTime.parse("3000-03-05T15:50:10");
    @Test
    void test_gameInProgressTrue() throws Exception {
        assertEquals(true, UserCommons.builder().startingDate(start).lastDate(end).build().gameInProgress());
    }
    @Test
    void test_gameInProgress_False_Not_Started() throws Exception {
        assertEquals(false, UserCommons.builder().startingDate(end).lastDate(end2).build().gameInProgress());
    }
    @Test
    void test_gameInProgress_False_Already_Ended() throws Exception {
        assertEquals(false, UserCommons.builder().startingDate(start).lastDate(start2).build().gameInProgress());
    }
}