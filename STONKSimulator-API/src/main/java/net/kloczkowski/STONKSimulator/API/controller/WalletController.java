package net.kloczkowski.STONKSimulator.API.controller;

import net.kloczkowski.STONKSimulator.API.dto.WalletDTO;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.function.Function;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<?> getWallet(Principal principal) {
        try {
            var payload = transformToDTO().apply(walletService.getByUser(principal.getName()));

            return new ResponseEntity<>(payload, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Function<Wallet, WalletDTO> transformToDTO() {
        return WalletDTO::new;
    }
}
