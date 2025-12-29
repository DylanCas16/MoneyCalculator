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
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        createInputArea(mainPanel);
        createOutputArea(mainPanel);
        createCalculateButton();

        this.add(mainPanel);
    }

    private void createInputArea(JPanel container) {
        JPanel panel = createColumnPanel();

        addCenteredLabel("From:", panel);
        fromCurrency = createCurrenciesField(panel);

        addCenteredLabel("Amount:", panel);
        inputTextField = createTextField(panel, true);

        panel.add(Box.createVerticalGlue());
        container.add(panel);
    }

    private void createOutputArea(JPanel container) {
        JPanel panel = createColumnPanel();

        addCenteredLabel("To:", panel);
        toCurrency = createCurrenciesField(panel);

        addCenteredLabel("Result:", panel);
        outputTextField = createTextField(panel, false);

        panel.add(Box.createVerticalGlue());
        container.add(panel);
    }

    private JPanel createColumnPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        return panel;
    }

    private void addCenteredLabel(String text, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
    }

    private JComboBox<Currency> createCurrenciesField(JPanel panel) {
        JComboBox<Currency> selector = new JComboBox<>(currenciesList.toArray(new Currency[0]));
        selector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(selector);
        return selector;
    }

    private JTextField createTextField(JPanel panel, boolean editable) {
        JTextField textField = new JTextField();
        textField.setEditable(editable);
        textField.setMaximumSize(new Dimension(200, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(textField);
        return textField;
    }

    private void createCalculateButton() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Calculate");

        button.setPreferredSize(new Dimension(150, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

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
        this.add(panel, BorderLayout.SOUTH);
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