package cz.mamuf.test.paymenttracker.model;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

@Test
public class CurrencyTest {

	private ArrayList<String> validCurrencies;
	private ArrayList<String> invalidCurrencies;

	@BeforeMethod
	public void beforeMethod() {
		validCurrencies = Lists.newArrayList("CZK", "EUR", "RUB", "GEL", "AZN", "VND", "AAA", "ZZZ");
		invalidCurrencies = Lists.newArrayList("cZK", "CzK", "CZk", "czK", "cZk", "Czk", "aaa", "zzz", "c", "C", "$",
				"#$~", "Koruny", "doláče");
	}

	@Test
	public void testValidCurrency() {
		validCurrencies.forEach(currency -> new Currency(currency));
	}

	@Test
	public void testInvalidCurrency() {
		invalidCurrencies.forEach(invalidCurrency -> {
			Assert.expectThrows(IllegalArgumentException.class, () -> new Currency(invalidCurrency));
		});
	}

	@Test
	public void testGetCurrency() {
		validCurrencies.forEach(currency -> {
			Assert.assertEquals(new Currency(currency).getCurrency(), currency);
		});
	}
}
