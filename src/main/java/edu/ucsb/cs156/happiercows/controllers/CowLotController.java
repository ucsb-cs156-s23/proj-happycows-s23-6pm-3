package edu.ucsb.cs156.happiercows.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs156.happiercows.repositories.UserCommonsRepository;
import edu.ucsb.cs156.happiercows.repositories.CowLotRepository;
import edu.ucsb.cs156.happiercows.entities.User;
import edu.ucsb.cs156.happiercows.entities.CowLot;
import edu.ucsb.cs156.happiercows.entities.UserCommons;
import edu.ucsb.cs156.happiercows.entities.Commons;
import edu.ucsb.cs156.happiercows.entities.CowLot;
import edu.ucsb.cs156.happiercows.errors.EntityNotFoundException;
import edu.ucsb.cs156.happiercows.errors.NoCowsException;
import edu.ucsb.cs156.happiercows.errors.NotEnoughMoneyException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Api(description = "Cow Lots")
@RequestMapping("/api/cowlots")
@RestController
public class CowLotController extends ApiController {

    @Autowired
    private UserCommonsRepository userCommonsRepository;

    @Autowired
    private CowLotRepository cowLotRepository;

    @Autowired
    ObjectMapper mapper;

    @ApiOperation(value = "Get all cow lots for current user:[[cowHealths...],[numCows...]]")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/forcurrentuser")
    public ResponseEntity<String> getCowLotsById(
        @ApiParam("commonsId") @RequestParam Long commonsId) throws JsonProcessingException {
        
        User u = getCurrentUser().getUser();
        Long userId = u.getId();
        UserCommons userCommons = userCommonsRepository.findByCommonsIdAndUserId(commonsId, userId)
            .orElseThrow(
                () -> new EntityNotFoundException(UserCommons.class, "commonsId", commonsId, "userId", userId));
        
        Long userCommonsId = userCommons.getId();

        Iterable<CowLot> cl = cowLotRepository.findAllByUserCommonsId(userCommonsId);
        
        List<List<Object>> array = new ArrayList<>();
        List<Object> healthList = new ArrayList<>();
        List<Object> numCowsList = new ArrayList<>();

        for (CowLot cowLot : cl) {
            healthList.add(cowLot.getHealth());
            numCowsList.add(cowLot.getNumCows());
        }

        array.add(healthList);
        array.add(numCowsList);

        String body = mapper.writeValueAsString(array);
        return ResponseEntity.ok().body(body);
  }
}
