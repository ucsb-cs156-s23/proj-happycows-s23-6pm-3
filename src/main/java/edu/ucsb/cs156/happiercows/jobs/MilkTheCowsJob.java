package edu.ucsb.cs156.happiercows.jobs;

import java.time.LocalDateTime;
import java.util.Date;

import edu.ucsb.cs156.happiercows.entities.Commons;
import edu.ucsb.cs156.happiercows.entities.Profit;
import edu.ucsb.cs156.happiercows.entities.User;
import edu.ucsb.cs156.happiercows.entities.UserCommons;
import edu.ucsb.cs156.happiercows.repositories.CommonsRepository;
import edu.ucsb.cs156.happiercows.repositories.ProfitRepository;
import edu.ucsb.cs156.happiercows.repositories.UserCommonsRepository;
import edu.ucsb.cs156.happiercows.repositories.UserRepository;
import edu.ucsb.cs156.happiercows.services.jobs.JobContext;
import edu.ucsb.cs156.happiercows.services.jobs.JobContextConsumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
public class MilkTheCowsJob implements JobContextConsumer {
    @Getter
    private CommonsRepository commonsRepository;
    @Getter
    private UserCommonsRepository userCommonsRepository;
    @Getter
    private UserRepository userRepository;
    @Getter
    private ProfitRepository profitRepository;

    public String formatDollars(double amount) {
        return  String.format("$%.2f", amount);
    }

    @Override
    public void accept(JobContext ctx) throws Exception {
            ctx.log("Starting Milk Cows job:");
            Iterable<Commons> allCommons = commonsRepository.findAll();
            for (Commons commons : allCommons) {
                String name = commons.getName();
                if(commons.gameInProgress()){
                    double milkPrice = commons.getMilkPrice();
                    ctx.log("Milking cows for Commons: " + name + ", Milk Price: " + formatDollars(milkPrice));
                    Iterable<UserCommons> allUserCommons = userCommonsRepository.findByCommonsId(commons.getId());
                    for (UserCommons userCommons : allUserCommons) {
                        milkCows(ctx, commons, userCommons);
                    }
                } else {
                    ctx.log("Commons " + name + " is not currently in progress, cows will not be milked in this commons.");
                }
            }
            ctx.log("Milk Cows job complete!");
        } 

    

    /** This method performs the function of milking the cows for a single userCommons. 
     *  It is a public method only so it can be exposed to the unit tests
     * @param ctx the JobContext
     * @param commons the Commons
     * @param userCommons the UserCommons
     **/

    public void milkCows(JobContext ctx, Commons commons, UserCommons userCommons) {
        User user = userRepository.findById(userCommons.getUserId()).orElseThrow(() -> new RuntimeException(
                "Error calling userRepository.findById(" + userCommons.getUserId() + ")"));
        ctx.log("User: " + user.getFullName() 
                + ", numCows: " + userCommons.getNumOfCows() 
                + ", cowHealth: " + userCommons.getCowHealth()
                + ", totalWealth: " + formatDollars(userCommons.getTotalWealth()));

        double profitAmount = calculateMilkingProfit(commons, userCommons);
        Profit profit = Profit.builder()
                .userCommons(userCommons)
                .amount(profitAmount)
                .timestamp(LocalDateTime.now())
                .numCows(userCommons.getNumOfCows())
                .avgCowHealth(userCommons.getCowHealth())
                .build();
        double newWeath = userCommons.getTotalWealth() + profitAmount;
        userCommons.setTotalWealth(newWeath);
        userCommonsRepository.save(userCommons);
        profit = profitRepository.save(profit);
        ctx.log("Profit for user: " + user.getFullName() 
                + " is: " + formatDollars(profitAmount)
                + ", newWealth: " + formatDollars(newWeath));
    }

    /**
     * Calculate the profit for a user from milking their cows.
     * 
     * @param userCommons
     * @return
     */
    public static double calculateMilkingProfit(Commons commons, UserCommons userCommons) {
        double milkPrice = commons.getMilkPrice();
        double profit = userCommons.getNumOfCows() * (userCommons.getCowHealth() / 100.0) * milkPrice;
        return profit;
    }
}
