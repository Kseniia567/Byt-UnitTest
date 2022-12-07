package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(Optional.of(1.5).get(), EUR.getRate());
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(1.0);
		assertEquals(Optional.of(1.0).get(), SEK.getRate());
	}
	
	@Test
	public void testGlobalValue() {
		var tempAmount = EUR.universalValue(100);
		assertEquals(Optional.of(150).get(), tempAmount);
	}
	
	@Test
	public void testValueInThisCurrency() {
		var amountSekToEur = EUR.valueInThisCurrency(1000, SEK);
		assertEquals(Optional.of(100).get(), amountSekToEur);
	}

}
