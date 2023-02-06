package busniss_logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class knows the SHORT NAMES of two currencies and it is able to calculate
 * the exchange value of a certain amount of the first one in terms of the other one
 *
 */
public class ForexOperator {

    private String sourceCurrency;
    private double amount;
    private String endCurrency;

    public ForexOperator (String source, double x, String end) {
        sourceCurrency = source;
        amount = x;
        endCurrency = end;
    }

    /**
     * @return				The exchange value as obtained in an online service
     * (currencyconvert.online). Yes, it is a sort of piracy, but only intended
     * to be used as a teaching example.
     *
     * @throws Exception	Several exceptions can be raised here, including
     * URLMalformedException (unlikely), IOException and NumberFormatException
     * (when the change value cannot be  obtained, e.g. because the currency
     * is not convertible). Additionally, if anything fails during the connection
     * and no numerical outcome is obtained, it also raises a generic exception.
     *
     */
    public double getChangeValue() throws Exception {

        // Constructing and invoking the URL that should return the currency conversion.
        // You can try it in a normal web browser.
        String urlText = "https://currencyconvert.online/" + sourceCurrency
                + "/" + endCurrency + "/" + amount;
        URL url = new URL(urlText);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(15*1000);
        connection.connect();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String current;
        int pos0, pos1;
        double sol = -1.0;

        // A quite primitive analysis of the content of the page
        while((current = in.readLine()) != null) {
            if (current.startsWith("Amount in words")) {
                pos0 = current.indexOf("? — ") + 4;
                while (current.charAt(pos0) < '0' || current.charAt(pos0) > '9')
                    pos0++;
                pos1 = current.indexOf(' ', pos0);
                sol = Double.parseDouble(current.substring(pos0, pos1));
                break;
            }
        }

        if (sol < 0)
            throw new Exception();    // The page has not been downloaded or is wrong

        return sol;
    }
}
