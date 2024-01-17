package org.stockmarket;

import org.stockmarket.model.Stock;
import org.stockmarket.model.StockType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockMarket {
    private final List<Stock> stocks;

    public StockMarket() {
        stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public List<String> listStocksByType(StockType type) {
        return stocks.stream()
                .filter(stock -> stock.getType() == type)
                .map(stock -> String.format("%s - %s - $%.2f", stock.getSymbol(), stock.getTypeDescription(), stock.getPrice()))
                .collect(Collectors.toList());
    }

    public void updateStockPrice(String symbol, double newPrice) {
        stocks.stream()
                .filter(stock -> stock.getSymbol().equals(symbol))
                .findFirst()
                .ifPresent(stock -> stock.setPrice(newPrice));
    }

    public Optional<Stock> getStock(String symbol) {
        return stocks.stream()
                .filter(stock -> stock.getSymbol().equals(symbol))
                .findFirst();
    }

    public List<Stock> getStocks() {
        return new ArrayList<>(stocks);
    }
}
