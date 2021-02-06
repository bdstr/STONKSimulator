package net.kloczkowski.STONKSimulator.API.controller;

import net.kloczkowski.STONKSimulator.API.dto.BuyPositionRequest;
import net.kloczkowski.STONKSimulator.API.dto.OpenPositionDTO;
import net.kloczkowski.STONKSimulator.API.dto.WalletDTO;
import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.service.WalletService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
            var wallet = walletToDTO().apply(walletService.getWalletByUser(principal.getName()));
            var positions = walletService.getOpenPositionsByUser(principal.getName());
            var positionsWithLinks = positions.stream()
                    .map(obj -> positionToDTO().apply(obj))
                    .map(obj -> addLinksForPosition().apply(obj))
                    .collect(Collectors.toList());
            Map<String, Object> payload = new HashMap<>();
            payload.put("wallet", wallet);
            payload.put("openPositions", positionsWithLinks);

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

    @GetMapping("/sell/{id}")
    public ResponseEntity<?> sellStock(Principal principal, @PathVariable("id") Long positionId) {
        try {
            var payload = walletService.placeSellStockOrder(principal.getName(), positionId);

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

    public Function<OpenPositionDTO, EntityModel<OpenPositionDTO>> addLinksForPosition() {
        return position -> EntityModel.of(position,
                linkTo(methodOn(WalletController.class).sellStock(null, position.getId())).withRel("sell"));
    }
}
