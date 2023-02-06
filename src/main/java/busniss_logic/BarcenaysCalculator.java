package busniss_logic;

public class BarcenaysCalculator implements ExchangeCalculator {


    @Override
    public String[] getCurrencyLongNames() {

        return Currency.longNames();
    }

    @Override
    public double getChangeValue(String origCurrency, double amount, String endCurrency) throws Exception {
        ForexOperator forex = new ForexOperator(origCurrency,amount, endCurrency);
        double result = forex.getChangeValue();
        return result;
    }

    // @Override
    public double calculateComission(double amount, String origCurrency) throws Exception {
        CommissionCalculator commission = new CommissionCalculator(amount, origCurrency);
        double calcul = commission.calculateCommission();
        return calcul;

    }
}
