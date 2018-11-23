package model;

import app.Settings;

import java.util.HashMap;

/**
 * Represents a currency symbol
 */
public class CurrencySymbol {

    /**
     * 3 letter ISO code for currencies
     */
    public static final String[] currencies = new String[] {
            "ADP","AED","AFA","AFN","ALK","ALL","AMD","ANG","AOA","AOK","AON","AOR","ARA","ARL","ARM","ARP","ARS","ATS","AUD","AWG",
            "AZM","AZN","BAD","BAM","BAN","BBD","BDT","BEC","BEF","BEL","BGL","BGM","BGN","BGO","BHD","BIF","BMD","BND","BOB","BOL",
            "BOP","BOV","BRB","BRC","BRE","BRL","BRN","BRR","BRZ","BSD","BTN","BUK","BWP","BYB","BYR","BZD","CAD","CDF","CHE","CHF",
            "CHW","CLE","CLF","CLP","CNX","CNY","COP","COU","CRC","CSD","CSK","CUC","CUP","CVE","CYP","CZK","DDM","DEM","DJF","DKK",
            "DOP","DZD","ECS","ECV","EEK","EGP","EQE","ERN","ESA","ESB","ESP","ETB","EUR","FIM","FJD","FKP","FRF","GBP","GEK","GEL",
            "GHC","GHS","GIP","GMD","GNF","GNS","GQE","GRD","GTQ","GWE","GWP","GYD","HKD","HNL","HRD","HRK","HTG","HUF","IDR","IEP",
            "ILP","ILR","ILS","INR","IQD","IRR","ISJ","ISK","ITL","JMD","JOD","JPY","KES","KGS","KHR","KMF","KPW","KRH","KRO","KRW",
            "KWD","KYD","KZT","LAK","LBP","LKR","LRD","LSL","LSM","LTL","LTT","LUC","LUF","LUL","LVL","LVR","LYD","MAD","MAF","MCF",
            "MDC","MDL","MGA","MGF","MKD","MKN","MLF","MMK","MNT","MOP","MRO","MTL","MTP","MUR","MVP","MVR","MWK","MXN","MXP","MXV",
            "MYR","MZE","MZM","MZN","NAD","NGN","NIC","NIO","NLG","NOK","NPR","NZD","OMR","PAB","PEI","PEN","PES","PGK","PHP","PKR",
            "PLN","PLZ","PTE","PYG","QAR","RHD","ROL","RON","RSD","RUB","RUR","RWF","SAR","SBD","SCR","SDD","SDG","SDP","SEK","SGD",
            "SHP","SIT","SKK","SLL","SOS","SRD","SRG","STD","SUR","SVC","SYP","SZL","THB","TJR","TJS","TMM","TMT","TND","TOP","TPE",
            "TRL","TRY","TTD","TWD","TZS","UAH","UAK","UGS","UGX","USD","USN","USS","UYI","UYP","UYU","UZS","VEB","VEF","VND","VNN",
            "VUV","WST","XAF","XAG","XAU","XBA","XBB","XBC","XBD","XCD","XDR","XEU","XFO","XFU","XOF","XPD","XPF","XPT","XRE","XTS",
            "XXX","YDD","YER","YUD","YUM","YUN","YUR","ZAL","ZAR","ZMK","ZRN","ZRZ","ZWL","ZWR","ZWD" };

    /**
     * Currency symbol mapping used for all currencies specified in order of {@link #currencies}
     */
    public static final String[] currencySymbols = new String[] {
            "ADP","AED","AFA","Af","ALK","ALL","AMD","NAf.","Kz","AOK","AON","AOR","₳","$L","m$n","ARP","AR$","ATS","AU$","Afl.",
            "AZM","man.","BAD","KM","BAN","Bds$","Tk","BEC","BF","BEL","BGL","BGM","BGN","BGO","BD","FBu","BD$","BN$","Bs","BOL",
            "$b.","BOV","BRB","BRC","BRE","R$","BRN","BRR","BRZ","BS$","Nu.","BUK","BWP","BYB","BYR","BZ$","CA$","CDF","CHE","CHF",
            "CHW","Eº","CLF","CL$","CNX","CN¥","CO$","COU","₡","CSD","CSK","CUC$","CU$","CV$","CY£","Kč","DDM","DM","Fdj","Dkr",
            "RD$","DA","ECS","ECV","Ekr","EGP","(null)","Nfk","ESA","ESB","Pts","Br","€","mk","FJ$","FK£","₣","£","GEK","GEL",
            "₵","GH₵","GI£","GMD","FG","GNS","GQE","₯","GTQ","GWE","GWP","GY$","HK$","HNL","HRD","kn","HTG","Ft","Rp","IR£",
            "I£","ILR","₪","₹","IQD","IRR","ISJ","Ikr","IT₤","J$","JD","¥","Ksh","KGS","KHR","CF","KPW","KRH","KRO","₩",
            "KD","KY$","KZT","₭","LB£","SLRs","L$","LSL","(null)","Lt","LTT","LUC","LUF","LUL","Ls","LVR","LD","MAD","MAF","MCF",
            "MDC","MDL","MGA","MGF","MKD","MKN","MLF","MMK","₮","MOP$","UM","Lm","MT£","MURs","MVP","MVR","MWK","MX$","MXP","MXV",
            "RM","MZE","Mt","MTn","N$","₦","NIC","C$","fl","Nkr","NPRs","NZ$","OMR","B/.","I/.","S/.","PES","PGK","₱","PKRs",
            "zł","PLZ","Esc","₲","QR","RH$","ROL","RON","din.","RUB","RUR","RWF","SR","SI$","SRe","LSd","SDG","SDP","Skr","S$",
            "SH£","SIT","Sk","Le","Ssh","SR$","Sf","Db","SUR","SV₡","SY£","SZL","฿","TJR","TJS","TMM","TMT","DT","T$","TPE",
            "TRL","TL","TT$","NT$","TSh","₴","UAK","UGS","USh","$","USN","USS","UYI","UYP","$U","UZS","VEB","Bs.F.","₫","VNN",
            "VT","WS$","FCFA","XAG","XAU","XBA","XBB","XBC","XBD","EC$","XDR","XEU","XFO","XFU","CFA","XPD","CFPF","XPT","XRE","XTS",
            "XXX","YDD","YR","YUD","YUM","YUN","YUR","ZAL","R","ZK","NZ","ZRZ","ZWL","ZWR","Z$" };

    /**
     * Symbol for the currency
     */
    private String symbol;

    /**
     * Must always be put after the number. When set to false a setting called {@link Settings#currencySymbolsAfter} can force the symbol to be always after the number.
     */
    private boolean alwaysAfter;

    /**
     * Currency ISO code -> CurrencySymbol mapping
     */
    private static final HashMap<String, CurrencySymbol> currencySignHashMap = new HashMap<>();

    /**
     * Currency symbol string -> Currency ISO code mapping
     */
    private static final HashMap<String, String> reverseCurrencySignHashMap = new HashMap<>();

    static
    {
        for (int i = 0; i < currencies.length; i++)
        {
            // Don't add symbols for ones that basically don't exist
            if (!currencySymbols[i].equals(currencies[i])) {
                // I don't know about other currencies, before after rules
                CurrencySymbol symbol = currencies[i].equals("HUF") ? new CurrencySymbol(currencySymbols[i], true) : new CurrencySymbol(currencySymbols[i], false);
                currencySignHashMap.put(currencies[i], symbol);
                reverseCurrencySignHashMap.put(symbol.getSymbol(), currencies[i]);
            }
        }
    }

    /**
     * Retrieves the symbol for a currency code
     * @param currencyCode Currency ISO code
     * @return CurrencySymbol object if exists, null otherwise
     */
    public static CurrencySymbol symbolFor(String currencyCode)
    {
        return currencySignHashMap.get(currencyCode);
    }

    /**
     * Retrieves the currency code for a symbol
     * @param currencySymbol Symbol string
     * @return Currency ISO code if symbol exists, null otherwise
     */
    public static String codeFor(String currencySymbol)
    {
        return reverseCurrencySignHashMap.get(currencySymbol);
    }

    /**
     * @param symbol Symbol for the currency
     * @param alwaysAfter Must always be put after the number. When set to false a setting called {@link Settings#currencySymbolsAfter} can force the symbol to be always after the number.
     */
    public CurrencySymbol(String symbol, boolean alwaysAfter) {
        this.symbol = symbol;
        this.alwaysAfter = alwaysAfter;
    }

    /**
     * @return Symbol for the currency
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return Must always be put after the number. When set to false a setting called {@link Settings#currencySymbolsAfter} can force the symbol to be always after the number.
     */
    public boolean isAlwaysAfter() {
        return alwaysAfter;
    }
}
