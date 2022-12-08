package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", 10, 20, new Money(9000, SEK), SweBank, "Alice");
		assertTrue("Timed payment exists", testAccount.timedPaymentExists("1"));

		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1", 10, 0, new Money(10000000, SEK), SweBank, "Alice");
		testAccount.tick();//fail, an error occurred in method Tick() class Account
		assertEquals(0, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(10000000, SEK));
		assertEquals(0, testAccount.getBalance().getAmount().intValue());
	}
	
	@Test
	public void testGetBalance() {

		var balance = testAccount.getBalance();
		assertEquals(10000000, balance.getAmount().intValue());
	}
}
