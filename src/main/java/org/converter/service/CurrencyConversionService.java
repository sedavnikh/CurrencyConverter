package org.converter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.converter.domain.ConversionHistory;
import org.converter.repository.ConversionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class CurrencyConversionService {

    @Value("${currencylayer.api.key}")
    private String apiKey;

    @Value("${currencylayer.api.query.list}")
    private String apiListQuery;

    @Value("${currencylayer.api.query.live}")
    private String apiLiveQuery;

    @Value("${currencylayer.api.query.historical}")
    private String apiHistoricalQuery;

    private ConversionHistoryRepository historyRepository;

    @Autowired
    public CurrencyConversionService(ConversionHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    private List<String> currencies;

    public List<String> getCurrencies() throws Exception {
        return this.currencies;
    }

    public String calculate(Long userId, String currencyFrom, String currencyTo, Long amount, Optional<String> date) throws Exception {
        JsonNode responseJson;
        if(date.isPresent()) {
            responseJson = sendGet(String.format(apiHistoricalQuery, apiKey, currencyFrom, currencyTo, date.get()));
        } else {
            responseJson = sendGet(String.format(apiLiveQuery, apiKey, currencyFrom, currencyTo));
        }
        JsonNode quotesNode = responseJson.get("quotes");
        Double quote = quotesNode.get(currencyFrom+currencyTo).asDouble();

        String result = String.valueOf(amount * quote);
        saveHistory(userId, currencyFrom, currencyTo, amount.toString(), date.orElse(null), result);
        return result;
    }

    public List<ConversionHistory> getUserHistory(Long userId) {
        return historyRepository.findTop10ByUserIdOrderByTimestampDesc(userId);
    }

    @PostConstruct
    private void initCurrencies() throws Exception {
        JsonNode responseJson = sendGet(String.format(apiListQuery, apiKey));
        JsonNode currenciesNode = responseJson.get("currencies");
        List<String> currencies = new ArrayList<>();
        Iterator<String> currenciesIterator = currenciesNode.fieldNames();
        while (currenciesIterator.hasNext()) {
            currencies.add(currenciesIterator.next());
        }
        this.currencies = Collections.unmodifiableList(currencies);
    }

    private JsonNode sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        StringBuffer response = new StringBuffer();
        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.toString());
    }

    private void saveHistory(Long userId, String currencyFrom, String currencyTo, String amount, String historicalDate, String result) {
        ConversionHistory history = new ConversionHistory(userId, currencyFrom, currencyTo, amount, result, historicalDate, new Date());
        historyRepository.save(history);
    }

}
