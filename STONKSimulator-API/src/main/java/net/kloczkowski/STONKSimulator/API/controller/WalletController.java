package net.kloczkowski.STONKSimulator.API.controller;

import net.kloczkowski.STONKSimulator.API.dto.BuyPositionRequest;
import net.kloczkowski.STONKSimulator.API.dto.OpenPositionDTO;
import net.kloczkowski.STONKSimulator.API.dto.WalletDTO;
import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            var payload = walletToDTO().apply(walletService.getByUser(principal.getName()));

            return new ResponseEntity<>(payload, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyStock(Principal principal, @RequestBody BuyPositionRequest buyPositionRequest) {
        try {
            var payload = positionToDTO().apply(walletService.placeBuyStockOrder(principal.getName(),
                    buyPositionRequest.getStockSymbol(),
                    buyPositionRequest.getVolume()));

            return new ResponseEntity<>(payload, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Function<Wallet, WalletDTO> walletToDTO() {
        return WalletDTO::new;
    }

    private Function<OpenPosition, OpenPositionDTO> positionToDTO() {
        return OpenPositionDTO::new;
    }
}
