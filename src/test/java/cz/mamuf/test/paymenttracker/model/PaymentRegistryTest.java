package cz.mamuf.test.paymenttracker.model;

import java.util.Map;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.mamuf.test.paymenttracker.model.PaymentRegistry.PaymentRegistryListener;

public class PaymentRegistryTest {

	private final Currency USD = new Currency("USD");
	private final Currency CZK = new Currency("CZK");

	@Test
	public void testAdd() {
		final Payment p1 = new Payment(USD, 1);

		final PaymentRegistry reg = new PaymentRegistry();
		reg.add(p1);

		Assert.assertEquals(reg.getTotalByCurrency(USD), p1.getValue());
	}

	@Test
	public void testGetTotalByCurrency() {
		final Payment p1usd = new Payment(USD, 1);
		final Payment p2usd = new Payment(USD, 2);
		final Payment p3czk = new Payment(CZK, 3);
		final Payment p4czk = new Payment(CZK, 4);

		final PaymentRegistry reg = new PaymentRegistry();
		reg.add(p1usd);
		reg.add(p2usd);
		reg.add(p3czk);
		reg.add(p4czk);

		Assert.assertEquals(reg.getTotalByCurrency(USD), p1usd.getValue() + p2usd.getValue());
		Assert.assertEquals(reg.getTotalByCurrency(CZK), p3czk.getValue() + p4czk.getValue());
	}

	@Test
	public void testGetTotalsByCurrencies() {
		final Payment p1usd = new Payment(USD, 1);
		final Payment p2usd = new Payment(USD, 2);
		final Payment p3czk = new Payment(CZK, 3);
		final Payment p4czk = new Payment(CZK, 4);

		final PaymentRegistry reg = new PaymentRegistry();
		reg.add(p1usd);
		reg.add(p2usd);
		reg.add(p3czk);
		reg.add(p4czk);

		final Map<Currency, Integer> totals = reg.getTotalsByCurrencies();

		Assert.assertEquals((int) totals.get(USD), p1usd.getValue() + p2usd.getValue());
		Assert.assertEquals((int) totals.get(CZK), p3czk.getValue() + p4czk.getValue());
	}

	@Test
	public void testListenerPaymentAdded() {
		final PaymentRegistryListener listenerMock = Mockito.mock(PaymentRegistryListener.class);
		final Payment p1 = new Payment(USD, 1);

		final PaymentRegistry reg = new PaymentRegistry();
		reg.addListener(listenerMock);
		reg.add(p1);

		Mockito.verify(listenerMock).paymentAdded(p1);
	}
}
