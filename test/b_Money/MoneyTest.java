package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(Optional.of(10000.0).get(), SEK100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
	}

	@Test
	public void testToString() {
		String line = "100.0 SEK";
		assertEquals(line, SEK100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(SEK.universalValue(10000), SEK100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue("Money are equal", SEK0.equals(EUR0));
		assertFalse("Money are not equal", SEK0.equals(SEK100));
	}

	@Test
	public void testAdd() {
		var sum = SEK100.add(EUR10);
		assertEquals(Optional.of(20000.0).get(), sum.getAmount());
	}

	@Test
	public void testSub() {

		var sub = SEK100.sub(EUR10);
		assertEquals(Optional.of(0.0).get(), sub.getAmount());
	}

	@Test
	public void testIsZero() {
		assertFalse("Not zero", SEK100.isZero());
		assertTrue("Is zero", EUR0.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(Optional.of(-10000.0).get(), SEK100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		assertEquals(1, SEK200.compareTo(EUR10));
		assertEquals(-1, SEK0.compareTo(EUR10));
	}
}
