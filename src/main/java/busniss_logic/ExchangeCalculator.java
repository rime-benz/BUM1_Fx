package busniss_logic;

public interface ExchangeCalculator {
    String[] getCurrencyLongNames();
    double getChangeValue(String origCurrency, double amount, String endCurrency) throws Exception;
    double calculateComission(double amount, String origCurrency) throws Exception;
}
