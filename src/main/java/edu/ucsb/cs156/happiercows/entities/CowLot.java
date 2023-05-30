package edu.ucsb.cs156.happiercows.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "cowlots")

public class CowLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="commons_id")
    private long commonsId;  

    @Column(name="user_id")
    private long userId;  

    private int numCows;
    private double health;
}