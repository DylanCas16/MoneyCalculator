package software.ulpgc.io;

import software.ulpgc.model.Currency;
import java.util.List;

public interface CurrencyLoader {
    List<Currency> loadAll();
}
