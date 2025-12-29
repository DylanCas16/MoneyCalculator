package software.ulpgc.application;

import software.ulpgc.control.ExchangeMoneyCommand;
import software.ulpgc.io.ExchangeRateLoader;
import software.ulpgc.model.Currency;
import software.ulpgc.model.Money;
import software.ulpgc.view.CurrencyDialog;
import software.ulpgc.view.MoneyDialog;
import software.ulpgc.view.MoneyDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Desktop extends JFrame {
    private JTextField inputTextField;
    private JTextField outputTextField;
    private final List<Currency> currenciesList;
    private final ExchangeRateLoader exchangeRate;
    private JComboBox<Currency> fromCurrency;
    private JComboBox<Currency> toCurrency;

    public Desktop(List<Currency> currencies, WebLoaders.WebExchangeRateLoader exchangeLoader) {
        currenciesList = currencies;
        exchangeRate = exchangeLoader;
        setTitle("Money Calculator");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel screen = new JPanel();
        setLayout(new GridLayout(1, 2));

        createInputArea();
        createOutputArea();

        this.add(screen);
    }

    private void createInputArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("From:"));
        fromCurrency = createCurrenciesField(panel);

        panel.add(new JLabel("Amount:"));
        inputTextField = createTextField(panel, true);

        createCalculateButton(panel);
        this.add(panel);
    }

    private void createOutputArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("To:"));
        toCurrency = createCurrenciesField(panel);

        panel.add(new JLabel("Result:"));
        outputTextField = createTextField(panel, false);
        this.add(panel);
    }

    private JComboBox<Currency> createCurrenciesField(JPanel panel) {
        JComboBox<Currency> selector = new JComboBox<>(currenciesList.toArray(new Currency[0]));
        selector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(selector);
        return selector;
    }

    private void createCalculateButton(JPanel panel) {
        JButton button = new JButton("Calculate");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> {
            try {
                ExchangeMoneyCommand command = new ExchangeMoneyCommand(
                        createMoneyDisplay(),
                        createCurrencyDialog(),
                        createMoneyDialog(),
                        exchangeRate
                );
                command.execute();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please, introduce a valid number.");
            }
        });
        panel.add(button);
    }

    private JTextField createTextField(JPanel panel, boolean editable) {
        JTextField textField = new JTextField(10);
        textField.setEditable(editable);
        panel.add(textField);
        return textField;
    }

    private MoneyDisplay createMoneyDisplay() {
        return money -> outputTextField.setText(String.format("%.2f %s", money.value(), money.currency().code()));
    }

    private CurrencyDialog createCurrencyDialog() {
        return () -> (Currency) toCurrency.getSelectedItem();
    }

    private MoneyDialog createMoneyDialog() {
        return () -> new Money(
                Double.parseDouble(inputTextField.getText()),
                (Currency) fromCurrency.getSelectedItem()
        );
    }
}