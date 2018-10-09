package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainJavaConfig {

    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(JavaConfiguration.class);
        CurrencyConvertor currencyConvertor = appContext.getBean("currrencyConvertorBean", CurrencyConvertor.class);
        System.out.println("One euro to czk: " +
                currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.valueOf(1)));
        //System.out.println(String.join(",\n", appContext.getBeanDefinitionNames()));

    }

}
