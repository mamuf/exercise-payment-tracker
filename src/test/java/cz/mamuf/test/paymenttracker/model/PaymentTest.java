package cz.mamuf.test.paymenttracker.model;

import static cz.mamuf.test.paymenttracker.TestUtils.arrayWrap;
import static org.testng.Assert.assertEquals;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PaymentTest {

	private static final Currency CZK = new Currency("CZK");
	private static final Currency USD = new Currency("USD");

	@DataProvider(name = "values")
	public Object[][] provideValues() {
		return arrayWrap(Stream.of(0, 1, 100, -350));
	}

	@Test(dataProvider = "values")
	public void testGetValue(final Integer value) {
		assertEquals(new Payment(CZK, value).getValue(), (int) value);
	}

	@DataProvider(name = "currencies")
	public Object[][] provideCurrencies() {
		return arrayWrap(Stream.of("CZK", "EUR", "RUB", "GEL", "AZN", "VND", "AAA", "ZZZ").map(s -> new Currency(s)));
	}

	@Test(dataProvider = "currencies")
	public void testGetCurrency(final Currency currency) {
		assertEquals(new Payment(currency, 5).getCurrency(), currency);
	}

	@DataProvider(name = "parsePayment")
	public Object[][] provideParsePaymentData() {
		return new Object[][] {
				{ "CZK 1", CZK, 1 },
				{ "USD -100", USD, -100 },
		};
	}

	@Test(dataProvider = "parsePayment")
	public void testParsePayment(final String input, final Currency currency, final Integer value) {
		assertEquals(Payment.parsePayment(input), new Payment(currency, value));
	}

	@DataProvider(name = "paymentParams")
	public Object[][] providePaymentParams() {
		return new Object[][] {
				{ CZK, 1 },
				{ USD, -10 }
		};
	}

	@Test(dataProvider = "paymentParams")
	public void testEquals(final Currency currency, final Integer value) {
		assertEquals(new Payment(currency, value), new Payment(currency, value));
	}

	@DataProvider(name = "parsePaymentInvalidData")
	public Object[][] provideParsePaymentInvalidData() {
		return arrayWrap(Stream.of("CZ 1", "USD x", "foo 5", "", "1", "CZK", "bla1", "foobar"));
	}

	@Test(dataProvider = "parsePaymentInvalidData", expectedExceptions = IllegalArgumentException.class)
	public void testParsePaymentInvalidArgument(final String input) {
		Payment.parsePayment(input);
	}
}
