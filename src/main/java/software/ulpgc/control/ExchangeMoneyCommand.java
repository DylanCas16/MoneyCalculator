package software.ulpgc.control;

import software.ulpgc.io.ExchangeRateLoader;
import software.ulpgc.model.Currency;
import software.ulpgc.model.ExchangeRate;
import software.ulpgc.model.Money;
import software.ulpgc.view.CurrencyDialog;
import software.ulpgc.view.MoneyDialog;
import software.ulpgc.view.MoneyDisplay;

public class ExchangeMoneyCommand implements Command {
    private final MoneyDisplay moneyDisplay;
    private final CurrencyDialog currencyDialog;
    private final MoneyDialog moneyDialog;
    private final ExchangeRateLoader exchangeRateLoader;

    public ExchangeMoneyCommand(MoneyDisplay moneyDisplay, CurrencyDialog currencyDialog, MoneyDialog moneyDialog, ExchangeRateLoader exchangeRateLoader) {
        this.moneyDisplay = moneyDisplay;
        this.currencyDialog = currencyDialog;
        this.moneyDialog = moneyDialog;
        this.exchangeRateLoader = exchangeRateLoader;
    }

    @Override
    public void execute() {
        Money money = moneyDialog.get();
        Currency currency = currencyDialog.get();

        ExchangeRate exchangeRate = exchangeRateLoader.load(currency, money.currency());
        Money result = new Money(money.value() * exchangeRate.rate(), currency);
        moneyDisplay.show(result);
    }
}
