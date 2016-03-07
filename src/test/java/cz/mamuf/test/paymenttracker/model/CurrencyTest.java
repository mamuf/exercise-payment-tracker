package cz.mamuf.test.paymenttracker.model;

import static cz.mamuf.test.paymenttracker.TestUtils.arrayWrap;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CurrencyTest {

	@DataProvider(name = "validCurrencies")
	public Object[][] provideValidCurrencies() {
		return arrayWrap(Stream.of("CZK", "EUR", "RUB", "GEL", "AZN", "VND", "AAA", "ZZZ"));
	}

	@DataProvider(name = "invalidCurrencies")
	public Object[][] provideInvalidCurrencies() {
		return arrayWrap(Stream.of("cZK", "CzK", "CZk", "czK", "cZk", "Czk", "aaa", "zzz", "c", "C", "$",
				"#$~", "Koruny", "doláče"));
	}

	@Test(dataProvider = "validCurrencies")
	@SuppressWarnings("unused")
	public void testValidCurrency(final String currency) {
		new Currency(currency);
	}

	@Test(dataProvider = "invalidCurrencies", expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("unused")
	public void testInvalidCurrency(final String invalidCurrency) {
		new Currency(invalidCurrency);
	}

	@Test(dataProvider = "validCurrencies")
	public void testGetCurrency(final String currency) {
		assertEquals(new Currency(currency).getCurrency(), currency);
	}

	@Test(dataProvider = "validCurrencies")
	public void testEquals(final String currency) {
		assertEquals(new Currency(currency), new Currency(currency));
	}

	@Test
	public void testNotEquals() {
		assertNotEquals(new Currency("CZK"), new Currency("USD"));
	}

	@Test(dataProvider = "validCurrencies")
	public void testHashCode(final String currency) {
		assertEquals(new Currency(currency).hashCode(), new Currency(currency).hashCode());
	}

	@Test
	public void testComparable() {
		assertEquals(new Currency("CZK").compareTo(new Currency("CZK")), 0, "currencies not equal");
		assertTrue(new Currency("AAA").compareTo(new Currency("ZZZ")) < 0, "AAA must be less than ZZZ");
		assertTrue(new Currency("ZZZ").compareTo(new Currency("AAA")) > 0, "ZZZ must be more than AAA");
	}
}
