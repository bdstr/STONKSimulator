package net.kloczkowski.STONKSimulator.API.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyPositionRequest {
    @JsonAlias(value = "stock_symbol")
    private String stockSymbol;
    private double volume;
}
