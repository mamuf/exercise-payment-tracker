package cz.mamuf.test.paymenttracker;

import static java.util.stream.Collectors.toList;

import java.util.stream.Stream;

public class TestUtils {

	/**
	 * Produces array of stream's elements wrapped into arrays.
	 */
	public static Object[][] arrayWrap(final Stream<?> stream) {
		return stream.map(s -> new Object[] { s })
				.collect(toList())
				.toArray(new Object[0][]);
	}

}
