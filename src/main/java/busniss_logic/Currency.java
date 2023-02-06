package busniss_logic;

/**
 * An example of the usefulness of enum Java types.
 * In the future Currencies can be added or removed and the list of values will be the
 * only code that will need to be altered.
 *
 */
public enum Currency {

    ARS		("Argentine Peso"),
    AUD		("Australian Dollar"),
    BRL		("Brazilian Real"),
    BTC		("Bitcoin"),
    CAD		("Canadian Dollar"),
    CHF		("Swiss Franc"),
    CNY		("Chinese Yuan Renminbi"),
    EUR		("Euro"),
    GBP		("British Pound Sterling"),
    IDR		("Indonesian Rupiah"),
    INR		("Indian Rupee"),
    IRR		("Iranian Rial"),
    JPY		("Japanese Yen"),
    MXN		("Mexican Peso"),
    NGN		("Nigerian Naira"),
    NZD		("New Zealand Dollar"),
    RUB		("Russian Ruble"),
    SEK		("Swedish Krona"),
    THB		("Thai Baht"),
    TRY		("Turkish Lira"),
    USD		("United States Dollar"),
    VES		("Venezuelan Bolivar"),
    ZAR		("South African Rand");

    private final String longName;


    Currency (String longName) {
        this.longName = longName;
    }

    /**
     * @return 					an array with the textual description of
     * the existing values in the enum type.
     *
     */
    public static String[] longNames() {
        String[] result = new String[Currency.values().length];
        for (Currency c : Currency.values()) {
            result[c.ordinal()] = c.name().concat(" - ").concat(c.longName);
        }
        return result;
    }
}
