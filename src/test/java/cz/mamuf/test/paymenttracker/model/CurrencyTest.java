package cz.mamuf.test.paymenttracker.model;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class CurrencyTest {

	private List<String> validCurrencies;
	private List<String> invalidCurrencies;

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

	@Test
	public void testEquals() {
		validCurrencies.forEach(c -> {
			Assert.assertEquals(new Currency(c), new Currency(c));
		});
	}

	@Test
	public void testNotEquals() {
		Assert.assertNotEquals(new Currency("CZK"), new Currency("USD"));
	}

	@Test
	public void testHashCode() {
		validCurrencies.forEach(c -> {
			Assert.assertEquals(new Currency(c).hashCode(), new Currency(c).hashCode());
		});
	}

	@Test
	public void testComparable() {
		Assert.assertEquals(new Currency("CZK").compareTo(new Currency("CZK")), 0, "currencies not equal");
		Assert.assertTrue(new Currency("AAA").compareTo(new Currency("ZZZ")) < 0, "AAA must be less than ZZZ");
		Assert.assertTrue(new Currency("ZZZ").compareTo(new Currency("AAA")) > 0, "ZZZ must be more than AAA");
	}
}
