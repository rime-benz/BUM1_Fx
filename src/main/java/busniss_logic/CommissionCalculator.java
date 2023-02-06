package busniss_logic;

/**
 * This class knows the result of a forex exchange operation in terms of the SHORT NAME
 * of a currency and its amount. It is able to calculate the commission to be applied
 * to this operation. Two important constants are owned by this class.
 *
 */
public class CommissionCalculator {

    private final static double MIN_COMMISION_IN_EUROS = 3.0;
    private final static double COMMISION_RATE = 0.0285;

    private double amount;
    private String currency;

    public CommissionCalculator (double quant, String curr) {
        this.amount = quant;
        this.currency = curr;
    }

    /**
     * @return				the commission to be applied, There is a fixed rate
     * but also a minimum amount (in euros), so that the commission will never
     * be smaller than that.
     */

    public double calculateCommission() throws Exception {
//		return 0.02851;
        double minCommission;
        if (this.currency.equals("EUR"))
            minCommission = MIN_COMMISION_IN_EUROS;
        else {
            ForexOperator fexop = new ForexOperator("EUR", MIN_COMMISION_IN_EUROS, this.currency);
            minCommission = fexop.getChangeValue();
        }
        double normalCommission = this.amount * COMMISION_RATE;
        return Math.max(minCommission, normalCommission);
    }
}
