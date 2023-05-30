package edu.ucsb.cs156.happiercows.strategies;

import edu.ucsb.cs156.happiercows.entities.Commons;
import edu.ucsb.cs156.happiercows.entities.UserCommons;

public interface CowPriceUpdateStrategy {

    public double calculateNewCowPrice(
            Commons commons,
            UserCommons user
    );

    public String getDisplayName();
    public String getDescription();
}