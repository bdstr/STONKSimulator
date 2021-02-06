package net.kloczkowski.STONKSimulator.API.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Relation(itemRelation = "sell")
public class OpenPositionDTO {

    private final Long id;
    private final LocalDateTime timestamp;
    private final String stockSymbol;
    private final double unitPrice;
    private final double volume;
    private final Long walletId;

    public OpenPositionDTO(OpenPosition position) {
        this.id = position.getId();
        this.timestamp = position.getTimestamp();
        this.stockSymbol = position.getStockSymbol();
        this.unitPrice = position.getUnitPrice();
        this.volume = position.getVolume();
        this.walletId = position.getWallet().getId();
    }
}
