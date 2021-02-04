package net.kloczkowski.STONKSimulator.API.fmcapi_client.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockPrice implements Serializable {

    String symbol;
    double price;
    long volume;
}
