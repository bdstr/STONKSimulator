package net.kloczkowski.STONKSimulator.API.service;

import net.kloczkowski.STONKSimulator.API.fmcapi_client.StockPrice;
import net.kloczkowski.STONKSimulator.API.fmcapi_client.StocksService;
import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.repository.OpenPositionRepository;
import net.kloczkowski.STONKSimulator.API.repository.WalletRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final OpenPositionRepository openPositionRepository;
    private final StocksService stocksService;

    public WalletService(WalletRepository walletRepository,
                         OpenPositionRepository openPositionRepository,
                         StocksService stocksService) {
        this.walletRepository = walletRepository;
        this.openPositionRepository = openPositionRepository;
        this.stocksService = stocksService;
    }

    public Wallet getByUser(String username) {
        return walletRepository.findByUser_Username(username).orElseThrow(EntityNotFoundException::new);
    }

    public OpenPosition placeBuyStockOrder(String username, String stockSymbol, double volume) throws Exception {
        StockPrice stockPrice = getStockPriceFromApi(stockSymbol);
        Wallet wallet = getByUser(username);
        var priceOfDesiredVolume = stockPrice.getPrice() * volume;

        if (wallet.getBalance() < priceOfDesiredVolume) {
            throw new Exception("Wallet balance too low");
        }

        payForStock(wallet, priceOfDesiredVolume);
        OpenPosition openPosition = new OpenPosition();
        openPosition.setWallet(wallet);
        openPosition.setUnitPrice(stockPrice.getPrice());
        openPosition.setVolume(volume);
        openPosition.setTimestamp(LocalDateTime.now());

        openPositionRepository.save(openPosition);
        return openPosition;
    }

    private StockPrice getStockPriceFromApi(String stockSymbol) throws Exception {
        var response = stocksService.getStockPrice(stockSymbol);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.stream(response.getBody())
                    .findFirst().orElseThrow(() -> new Exception("No such stock found"));
        } else {
            throw new Exception("FMC Api unreachable");
        }
    }

    private void payForStock(Wallet wallet, double price) {
        var newWalletBalance = wallet.getBalance() - price;
        wallet.setBalance(newWalletBalance);
    }
}
