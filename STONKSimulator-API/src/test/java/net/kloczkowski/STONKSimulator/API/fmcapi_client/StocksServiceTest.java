package net.kloczkowski.STONKSimulator.API.fmcapi_client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StocksServiceTest {

    @Autowired
    StocksService stocksService;

    @Test
    void getStockPriceShouldCallApiAndReturnStockPrice() throws Exception {
        var result = stocksService.getStockPrice("AAPL");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("AAPL", Arrays.stream(result.getBody()).findFirst().get().getSymbol());
    }
}