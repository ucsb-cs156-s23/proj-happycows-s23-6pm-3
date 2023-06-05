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

@WebMvcTest(controllers = UserCommonsController.class)
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
    CowLot cowLot = new CowLot(1, 3, 4, 97.5);
    return cowLot;
  }

//   @WithMockUser(roles = { "USER" })
//   @Test
//   public void test_get_CowLot() throws Exception {
//     List<CowLot> expectedCowLot = new ArrayList<CowLot>();
//     CowLot testexpectedCowLot = dummyCowLot(1);
//     expectedCowLot.add(testexpectedCowLot);

//     UserCommons testUserCommons = UserCommons
//         .builder()
//         .id(1L)
//         .userId(1L)
//         .commonsId(1L)
//         .totalWealth(300)
//         .numOfCows(1)
//         .cowHealth(100)
//         .build();

//     Commons testCommons = Commons
//         .builder()
//         .name("test commons")
//         .cowPrice(10)
//         .milkPrice(2)
//         .startingBalance(300)
//         .startingDate(LocalDateTime.now())
//         .build();

//     MvcResult response = mockMvc.perform(get("/api/commons/new"))
//     .andExpect(status().isOk()).andReturn();

//     MvcResult response = mockMvc.perform(get("/api/cowlots/forcurrentuser?commonsId=1"))
//           .andExpect(status().isOk()).andReturn();

//     verify(cowLotRepository, times(1)).findAllByUserCommonsId(eq(1L));
//   }
}
