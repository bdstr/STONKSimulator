package net.kloczkowski.STONKSimulator.API.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "wallets")
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    @OneToOne(mappedBy = "wallet")
    private User user;
}
