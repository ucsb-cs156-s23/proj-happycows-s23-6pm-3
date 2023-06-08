package edu.ucsb.cs156.happiercows.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "commons")
public class Commons
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private double cowPrice;
  private double milkPrice;
  private double startingBalance;
  private LocalDateTime startingDate;
  private LocalDateTime lastDate;
  private double degradationRate;
  private boolean showLeaderboard;
  private int carryingCapacity;
  private double priceChange;
  public void increaseCowPrice() {
    cowPrice += priceChange;
  }
  public void decreaseCowPrice() {
    cowPrice -= priceChange;
  }
  public boolean gameInProgress(){
    boolean output = (startingDate.isBefore(LocalDateTime.now()) && lastDate.isAfter(LocalDateTime.now()));
    return output;
  }

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
  @JoinTable(name = "user_commons",
    joinColumns = @JoinColumn(name = "commons_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
  private List<User> users;
}
