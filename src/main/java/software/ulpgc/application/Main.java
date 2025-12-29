package software.ulpgc.application;

import software.ulpgc.model.Currency;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        WebLoaders.WebExchangeRateLoader exchangeLoader = new WebLoaders.WebExchangeRateLoader();

        WebLoaders.WebCurrencyLoader currencyLoader = new WebLoaders.WebCurrencyLoader();
        List<Currency> currencies = currencyLoader.loadAll();

        Desktop mainScreen = new Desktop(currencies, exchangeLoader);
        mainScreen.setVisible(true);
    }
}
