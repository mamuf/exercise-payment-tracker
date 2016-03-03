package cz.mamuf.test.paymenttracker.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PaymentTest {

	private static final Currency CURRENCY = new Currency("CZK");
	private List<Currency> validCurrencies;

	@BeforeMethod
	public void beforeMethod() {
		validCurrencies = Stream.of("CZK", "EUR", "RUB", "GEL", "AZN", "VND", "AAA", "ZZZ")
				.map(str -> new Currency(str))
				.collect(Collectors.toList());
	}

	@Test
	public void testGetValue() {
		Stream.of(0, 1, 100, -350).forEach(value -> {
			Assert.assertEquals(new Payment(CURRENCY, value).getValue(), (int) value);
		});
	}

	@Test
	public void testGetCurrency() {
		validCurrencies.forEach(currency -> {
			Assert.assertEquals(new Payment(currency, 5).getCurrency(), currency);
		});
	}
}
