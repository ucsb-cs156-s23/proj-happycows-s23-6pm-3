package edu.ucsb.cs156.happiercows.controllers;

import edu.ucsb.cs156.happiercows.ControllerTestCase;
import edu.ucsb.cs156.happiercows.repositories.UserRepository;
import edu.ucsb.cs156.happiercows.repositories.CommonsRepository;
import edu.ucsb.cs156.happiercows.repositories.UserCommonsRepository;
import edu.ucsb.cs156.happiercows.repositories.CowLotRepository;
import edu.ucsb.cs156.happiercows.entities.Commons;
import edu.ucsb.cs156.happiercows.entities.CowLot;
import edu.ucsb.cs156.happiercows.entities.User;
import edu.ucsb.cs156.happiercows.entities.UserCommons;
import edu.ucsb.cs156.happiercows.errors.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@WebMvcTest(controllers = {CowLotController.class, UserCommonsController.class})
@AutoConfigureDataJpa
public class CowLotControllerTests extends ControllerTestCase {

  @MockBean
  UserCommonsRepository userCommonsRepository;

  @MockBean
  UserRepository userRepository;

  @MockBean
  CommonsRepository commonsRepository;

  @MockBean
  CowLotRepository cowLotRepository;

  @Autowired
  private ObjectMapper objectMapper;

  public static CowLot dummyCowLot(long id) {
    CowLot cowLot = new CowLot(0, 1, 1, 100.0);
    return cowLot;
  }

  public static Commons dummyCommons(long id) {
    Commons commons = new Commons(id, "test", 1, 1, 1, LocalDateTime.now(), 1, true, 1, 1, new ArrayList<User>());
    return commons;
  }

  public static UserCommons dummyUserCommons(long id) {
    UserCommons userCommons = new UserCommons(id,1,1,"test",1,1, 100);
    return userCommons;
  }

  @WithMockUser(roles = { "USER" })
  @Test
  public void test_get_CowLot() throws Exception {
    //arrange cowLots
    List<CowLot> expectedCowLots = new ArrayList<CowLot>();
    CowLot testexpectedCowLot = dummyCowLot(1);
    expectedCowLots.add(testexpectedCowLot);
    when(cowLotRepository.findAllByUserCommonsId(1L)).thenReturn(expectedCowLots);

    //arrange other
    Commons expectedCommons = dummyCommons(1);
    UserCommons expectedUserCommons = dummyUserCommons(1);
    when(commonsRepository.findById(1L)).thenReturn(Optional.of(expectedCommons));
    when(userCommonsRepository.findByCommonsIdAndUserId(eq(1L),eq(1L))).thenReturn(Optional.of(expectedUserCommons));

    String requestBody = mapper.writeValueAsString(expectedUserCommons);

    // act
    MvcResult response = mockMvc.perform(put("/api/usercommons/buy?commonsId=1")
    .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBody)
                .with(csrf()))
        .andExpect(status().isOk()).andReturn();

    //assert
    verify(cowLotRepository, times(1)).save(testexpectedCowLot);
    
    // act
    MvcResult response2 = mockMvc.perform(get("/api/cowlots/forcurrentuser?commonsId=1").with(csrf()))
        .andExpect(status().isOk()).andReturn();
  }
}
