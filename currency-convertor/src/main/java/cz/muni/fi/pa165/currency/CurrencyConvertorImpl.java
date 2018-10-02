package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        BigDecimal exchangeRate;
        logger.trace("Conversion of {} {} to {}", sourceAmount, sourceCurrency, targetCurrency);

        if(null == sourceAmount || sourceCurrency == null || targetCurrency == null){
            throw new IllegalArgumentException("SourceAmount cant be null");
        }

        try {
            exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (null == exchangeRate){
                logger.debug("Exchange rate for given currencies ({}, {}) is not available", sourceCurrency, targetCurrency);
                throw new UnknownExchangeRateException("Exchange rate for given currencies is not available");
            }
        } catch (ExternalServiceFailureException e) {
            logger.error("Conversion failed for currencies {} and {}", sourceCurrency, targetCurrency);
            throw new UnknownExchangeRateException(e.getMessage());
        }

        return exchangeRate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

}
