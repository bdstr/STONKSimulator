package net.kloczkowski.STONKSimulator.API.fmcapi_client;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter(value = AccessLevel.PROTECTED)
public class StockPrice implements Serializable {

    String symbol;
    double price;
    long volume;
}
