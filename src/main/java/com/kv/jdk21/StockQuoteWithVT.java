package com.kv.jdk21;

import org.apache.commons.io.FileUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

import static com.kv.jdk21.YahooEndPoints.*;

public class StockQuoteWithVT {
    private final static String HOST_NAME = "alpha-vantage.p.rapidapi.com";
    private final static String API_KEY = "X-RapidAPI-Key";
    private final static String RAPID_API_HOST = "apidojo-yahoo-finance-v1.p.rapidapi.com";
    private final static String KEY = "src/main/resources/key.txt";

    private static String getKey() throws Exception {
        return FileUtils.readLines(FileUtils.getFile(KEY)).get(0);
    }

    private static String getQuote(String key, String series) throws Exception {
        //Make the thread sleep to avoid throttling from API provider.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(series))
                .header(API_KEY, key)
                .header(RAPID_API_HOST, HOST_NAME)
                .GET()
                .build();

        HttpResponse<String> response;
        try(HttpClient httpClient = HttpClient.newHttpClient()){
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.printf("URL: %s - Response Code: %d%n", series, response.statusCode());
        }
        return response.body();
    }

    private static YahooReports getTickerDetails(String key, String ticker) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<String> summary = scope.fork(() -> getQuote(key, YahooEndPoints.get(GET_SUMMARY, ticker)));
            Subtask<String> options = scope.fork(() -> getQuote(key, YahooEndPoints.get(GET_OPTIONS, ticker)));
            Subtask<String> topHoldings = scope.fork(() -> getQuote(key, YahooEndPoints.get(TOP_HOLDINGS, ticker)));
            Subtask<String> earnings = scope.fork(() -> getQuote(key, YahooEndPoints.get(GET_EARNINGS, ticker)));
            Subtask<String> financials = scope.fork(() -> getQuote(key, YahooEndPoints.get(GET_FINANCIALS, ticker)));
            scope.join();
            scope.throwIfFailed(AlphaVantageException::new);
            return new YahooReports(summary.get(),financials.get(),options.get(),topHoldings.get(),earnings.get());
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getTickerDetails(getKey(), "JPM"));
    }
}
