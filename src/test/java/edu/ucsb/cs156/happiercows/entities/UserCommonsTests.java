package edu.ucsb.cs156.happiercows.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ucsb.cs156.happiercows.entities.UserCommons;

import org.junit.jupiter.api.Test;

public class UserTests {
    @Test
    void test_gameInProgressTrue() {
        assertEquals(true, UserCommons.builder().startingDate(2020-01-21T06:47:22.756).lastDate(3000-12-17T15:59:19.51).build().gameInProgress());
    }
    @Test
    void test_gameInProgress_False_Not_Started() {
        assertEquals(false, UserCommons.builder().startingDate(3000-01-21T06:47:22.756).lastDate(3000-12-17T15:59:19.51).build().gameInProgress());
    }
    @Test
    void test_gameInProgress_False_Already_Ended() {
        assertEquals(false, UserCommons.builder().startingDate(2000-01-21T06:47:22.756).lastDate(2000-12-17T15:59:19.51).build().gameInProgress());
    }
}