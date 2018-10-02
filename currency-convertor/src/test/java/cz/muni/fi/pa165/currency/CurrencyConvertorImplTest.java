package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyConvertorImplTest {
    private CurrencyConvertor currencyConvertor;
    private ExchangeRateTable exchangeRateTable;

    @Before
    public void setUp() throws Exception {
        exchangeRateTable = mock(ExchangeRateTable.class);

        when(exchangeRateTable.getExchangeRate(Currency.getInstance("EUR"), Currency.getInstance("CZK")))
                .thenReturn(new BigDecimal(25));

        when(exchangeRateTable.getExchangeRate(Currency.getInstance("EUR"), Currency.getInstance("HUF")))
                .thenReturn(new BigDecimal(323.465957));

        when(exchangeRateTable.getExchangeRate(Currency.getInstance("HUF"), Currency.getInstance("HUF")))
                .thenThrow(new ExternalServiceFailureException("Unknown currency"));

        when(exchangeRateTable.getExchangeRate(Currency.getInstance("EUR"), Currency.getInstance("JPY")))
                .thenReturn(null);

        when(exchangeRateTable.getExchangeRate(isNull(), any(Currency.class)))
                .thenThrow(new IllegalArgumentException());

        when(exchangeRateTable.getExchangeRate(any(Currency.class), isNull()))
                .thenThrow(new IllegalArgumentException());


        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.

        BigDecimal result = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(10));
        assertEquals(exchangeRateTable.getExchangeRate(
                Currency.getInstance("EUR"),
                Currency.getInstance("CZK"))
                .multiply(new BigDecimal(10).setScale(2, BigDecimal.ROUND_HALF_EVEN)),
                result);

        result = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("HUF"), new BigDecimal(3));
        assertEquals(exchangeRateTable.getExchangeRate(
                Currency.getInstance("EUR"),
                Currency.getInstance("HUF"))
                .multiply(new BigDecimal(3)).setScale(2, BigDecimal.ROUND_HALF_EVEN),
                result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        currencyConvertor.convert(null, Currency.getInstance("CZK"), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        currencyConvertor.convert(Currency.getInstance("EUR"), null, new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {
        currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() {
        currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("JPY"), new BigDecimal(10));
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithExternalServiceFailure() {
        currencyConvertor.convert(Currency.getInstance("HUF"), Currency.getInstance("HUF"), new BigDecimal(10));
    }

}
