package software.ulpgc.application;

import software.ulpgc.model.Currency;
import software.ulpgc.model.ExchangeRate;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Currency> currencies = new WebLoaders.WebCurrencyLoader().loadAll();
        ExchangeRate exchangeRate = new WebLoaders.WebExchangeRateLoader().load(currencies.get(0), currencies.get(1));
        System.out.println(exchangeRate);
    }
}
