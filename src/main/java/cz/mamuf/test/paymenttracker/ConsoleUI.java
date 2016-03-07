package cz.mamuf.test.paymenttracker;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import cz.mamuf.test.paymenttracker.model.Currency;
import cz.mamuf.test.paymenttracker.model.Payment;
import cz.mamuf.test.paymenttracker.model.PaymentRegistry;

/**
 * Console-based UI.
 */
public class ConsoleUI {

	private static final String CMD_QUIT = "quit";
	private static final int PRINT_REGISTRY_PERIOD_SEC = 10;

	private final PaymentRegistry model;

	private final AtomicBoolean terminate = new AtomicBoolean(false);
	private final Thread outputThread = new Thread(() -> outputLoop());

	public ConsoleUI(final PaymentRegistry model) {
		this.model = model;
	}

	public void open() {
		System.out.println("Payment Tracker");
		System.out.println("===============");

		startPrintingThread();
		inputLoop();
		terminate();
	}

	private void startPrintingThread() {
		outputThread.start();
		try {
			// allow printing thread to do its first iteration
			Thread.sleep(100);
		} catch (final InterruptedException e) {
			// nop
		}
	}

	private void inputLoop() {
		while (!terminate.get()) {
			printInputPrompt();
			final String input = System.console().readLine();
			if ("".equals(input)) {
				continue;
			} else if (CMD_QUIT.equals(input)) {
				terminate.set(true);
				break;
			}
			handleInput(input);
		}
	}

	private void printInputPrompt() {
		System.out.print("Enter payment [CCC value] or 'quit': ");
	}

	private void handleInput(final String input) {
		try {
			final Payment payment = Payment.parsePayment(input);
			model.add(payment);
		} catch (final IllegalArgumentException e) {
			System.out.println("ERROR: Invalid input: '" + input + "'");
		}
	}

	private void outputLoop() {
		printCurrentBalance();
		if (!sleep(PRINT_REGISTRY_PERIOD_SEC)) {
			return;
		}

		while (!terminate.get()) {
			printCurrentBalance();
			handleBalancePrinted();
			if (!sleep(PRINT_REGISTRY_PERIOD_SEC)) {
				break;
			}
		}
	}

	private void printCurrentBalance() {
		System.out.println("\nCurrent balance:");
		final Map<Currency, Integer> totalsByCurrencies = model.getTotalsByCurrencies();
		if (totalsByCurrencies.isEmpty()) {
			System.out.println("--no payments--");
		} else {
			totalsByCurrencies.forEach((currency, value) -> {
				System.out.printf("%s %d\n", currency, value);
			});
		}
		System.out.println();
	}

	private void handleBalancePrinted() {
		printInputPrompt();
	}

	/**
	 * @return <code>false</code> if sleep was interrupted, otherwise
	 *         <code>true</code>
	 */
	private boolean sleep(final int sec) {
		try {
			Thread.sleep(sec * 1000);
			return true;
		} catch (final InterruptedException e) {
			return false;
		}
	}

	private void terminate() {
		outputThread.interrupt();
		try {
			outputThread.join();
		} catch (final InterruptedException e) {
			System.out.println(e.toString());
		} finally {
			System.out.println("Bye!");
		}
	}

}
