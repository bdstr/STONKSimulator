package net.kloczkowski.STONKSimulator.API.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "positions")
@Getter
@Setter
@RequiredArgsConstructor
public class OpenPosition implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    @Column(name = "unit_price")
    private double unitPrice;

    private double volume;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
