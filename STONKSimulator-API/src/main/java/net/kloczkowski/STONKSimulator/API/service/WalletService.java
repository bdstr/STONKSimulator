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
import java.util.Locale;

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
        StockPrice stockPrice = getStockPriceFromApi(stockSymbol.toUpperCase(Locale.ROOT));
        Wallet wallet = getByUser(username);
        var priceOfDesiredVolume = stockPrice.getPrice() * volume;

        if (wallet.getBalance() < priceOfDesiredVolume) {
            throw new Exception("Wallet balance too low");
        }

        subtractFromBalance(wallet, priceOfDesiredVolume);

        OpenPosition openPosition = new OpenPosition();
        openPosition.setWallet(wallet);
        openPosition.setStockSymbol(stockPrice.getSymbol());
        openPosition.setUnitPrice(stockPrice.getPrice());
        openPosition.setVolume(volume);
        openPosition.setTimestamp(LocalDateTime.now());

        openPositionRepository.save(openPosition);
        return openPosition;
    }

    public double placeSellStockOrder(String username, Long positionId) throws Exception {
        OpenPosition openPosition = openPositionRepository.findById(positionId).orElseThrow(() -> new Exception("Cannot find position with given id"));
        Wallet wallet = getByUser(username);

        if (!openPosition.getWallet().getUser().getUsername().equals(username)) {
            throw new Exception("Chosen position does not belong to current user");
        }

        StockPrice stockPrice = getStockPriceFromApi(openPosition.getStockSymbol());
        var currentValueOfHeldStocks = openPosition.getVolume() * stockPrice.getPrice();

        openPositionRepository.delete(openPosition);

        addToBalance(wallet, currentValueOfHeldStocks);
        return currentValueOfHeldStocks;
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

    private void subtractFromBalance(Wallet wallet, double amount) {
        var newWalletBalance = wallet.getBalance() - amount;
        wallet.setBalance(newWalletBalance);
        walletRepository.save(wallet);
    }

    private void addToBalance(Wallet wallet, double amount) {
        var newWalletBalance = wallet.getBalance() + amount;
        wallet.setBalance(newWalletBalance);
        walletRepository.save(wallet);
    }

}
