package net.kloczkowski.STONKSimulator.API.fmcapi_client;

import net.kloczkowski.STONKSimulator.API.fmcapi_client.model.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static net.kloczkowski.STONKSimulator.API.fmcapi_client.config.ApiConnectionConfiguration.API_KEY;

@Service
public class StocksService {

    @Autowired
    private RestTemplate restTemplate;

    public StocksService() {
    }

    public ResponseEntity<StockPrice[]> getStockPrice(String stockSymbol) {
        String endpointUri = "/quote-short/";
        String uri = String.format("%s%s?apikey=%s", endpointUri, stockSymbol, API_KEY);

        return restTemplate.getForEntity(uri, StockPrice[].class);
    }
}
