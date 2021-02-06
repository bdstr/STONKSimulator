package net.kloczkowski.STONKSimulator.API.fmcapi_client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static net.kloczkowski.STONKSimulator.API.fmcapi_client.ApiConnectionConfiguration.API_KEY;

@Service
public class StocksService {

    private final RestTemplate restTemplate;

    public StocksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<StockPrice[]> getStockPrice(String stockSymbol) {
        String endpointUri = "/quote-short/";
        String uri = String.format("%s%s?apikey=%s", endpointUri, stockSymbol, API_KEY);

        return restTemplate.getForEntity(uri, StockPrice[].class);
    }
}
