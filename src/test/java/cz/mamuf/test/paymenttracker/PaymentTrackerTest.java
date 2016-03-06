package cz.mamuf.test.paymenttracker;

import java.io.IOException;
import java.util.Collections;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.mamuf.test.paymenttracker.model.Currency;
import cz.mamuf.test.paymenttracker.model.Payment;

public class PaymentTrackerTest {

	@Test
	public void testConstructor() {
		final PaymentTracker tracker = new PaymentTracker();
		Assert.assertNotNull(tracker.getModel());
	}

	@Test
	public void testConstructorWithStore() throws IOException {
		final PaymentStore storeMock = Mockito.mock(PaymentStore.class);
		Mockito.when(storeMock.loadAll()).thenReturn(Collections.emptyList());

		final PaymentTracker tracker = new PaymentTracker(storeMock);

		Mockito.verify(storeMock).loadAll();
		Assert.assertNotNull(tracker.getModel());
	}

	@Test(expectedExceptions = PaymentTrackerException.class)
	@SuppressWarnings({ "unchecked", "unused" })
	public void testConstructorWithStoreException() throws IOException {
		final PaymentStore storeMock = Mockito.mock(PaymentStore.class);
		Mockito.when(storeMock.loadAll()).thenThrow(PaymentTrackerException.class);

		new PaymentTracker(storeMock);
	}


	@Test
	public void testAddPayment() throws IOException {
		final PaymentStore storeMock = Mockito.mock(PaymentStore.class);
		Mockito.when(storeMock.loadAll()).thenReturn(Collections.emptyList());

		final Payment p1 = new Payment(new Currency("USD"), 3);

		final PaymentTracker tracker = new PaymentTracker(storeMock);
		tracker.getModel().add(p1);

		Mockito.verify(storeMock).add(p1);
	}

}
