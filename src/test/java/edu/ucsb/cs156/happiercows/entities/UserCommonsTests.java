package edu.ucsb.cs156.happiercows.entities;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.ucsb.cs156.happiercows.entities.User;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UserCommonsTests {

    // DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    Date start = new Date("2020-01-21T06:47:22.756");
    Date start2 = new Date("2020-06-21T06:47:22.756");
    Date end = new Date("3000-01-21T06:47:22.756");
    Date end2 = new Date("3000-12-17T15:59:19.51");
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