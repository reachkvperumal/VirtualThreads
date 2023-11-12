package com.kv.jdk21;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum YahooEndPoints {
    GET_SUMMARY("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-summary?symbol=%s&region=US"),
    GET_FINANCIALS("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-financials?symbol=%s&region=US"),
    GET_OPTIONS("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-options?symbol=%s&region=US&lang=en-US&straddle=true"),
    TOP_HOLDINGS("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-top-holdings?symbol=%s&region=US&lang=en-US&straddle=true"),
    GET_EARNINGS("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-earnings?symbol=%s&region=US&lang=en-US");

    private String url;

    YahooEndPoints(String url) {
        this.url = url;
    }

    private static final Map<YahooEndPoints, String> KEY_VALUE = Arrays.stream(YahooEndPoints.values()).collect(Collectors.toMap(Function.identity(), v -> v.url));

    public static String get(YahooEndPoints s, String ticker) {
        return String.format(KEY_VALUE.get(s), ticker);
    }
}
