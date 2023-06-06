package edu.ucsb.cs156.happiercows.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Builder;
import lombok.AccessLevel;
import java.util.Date;
import java.time.LocalDateTime;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "user_commons")
public class UserCommons {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;  

  @Column(name="commons_id")
  private long commonsId;  

  @Column(name="user_id")
  private long userId;  

  private String username;

  private double totalWealth;

  private int numOfCows;

  private double cowHealth;

  private LocalDateTime startingDate;

  private LocalDateTime lastDate;

  public boolean gameInProgress(){
    boolean output = (startingDate.isBefore(LocalDateTime.now()) && lastDate.isAfter(LocalDateTime.now()));
    return output;
  }
}
