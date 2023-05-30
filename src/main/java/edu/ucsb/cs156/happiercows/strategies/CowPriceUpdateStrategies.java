package edu.ucsb.cs156.happiercows.strategies;

import edu.ucsb.cs156.happiercows.entities.Commons;
import edu.ucsb.cs156.happiercows.entities.UserCommons;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The PriceUpdateStrategies enum provides a variety of strategies for updating a cow's price.
 * 
 * For information on Java enum's, see the Oracle Java Tutorial on <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html">Enum Types</a>,
 * which are far more powerful in Java than enums in most other languages.
 */

@Getter
@AllArgsConstructor
public enum CowPriceUpdateStrategies implements CowPriceUpdateStrategy {

    Absolute("Absolute", "Cow health increases/decreases proportionally to the number of cows over/under the carrying capacity.") {
        @Override
        public double calculateNewCowPrice(Commons commons, UserCommons user) {
            if (/*plus or minus*/) {
                return commons.getCowPrice() + commons.getPriceChange();
            } else {
                return commons.getCowPrice() - commons.getPriceChange();
            }
        }
    },
    Relative("Relative", "Cow Price changes increases/decreases by the degradation rate, depending on if the number of cows exceeds the carrying capacity.") {
        @Override
        public double calculateNewCowPrice(Commons commons, UserCommons user) {
            if (/*plus or minus*/) {
                return commons.getCowPrice() + commons.getPriceChange();
            } else {
                return commons.getCowPrice() - commons.getPriceChange();
            }
        }
    },
    Noop("Do nothing", "Cow Price does not change.") {
        @Override
        public double calculateNewCowPrice(Commons commons, UserCommons user) {
            return user.getCowPrice();
        }
    };

    private final String displayName;
    private final String description;
}