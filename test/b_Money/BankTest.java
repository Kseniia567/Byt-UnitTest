package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("SEK", SweBank.getCurrency().getName());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException { //fail
		//an error occurred in method openAccount()
		SweBank.openAccount("Kseniia");
		SweBank.deposit("Kseniia", new Money(100000000, SEK));
		assertEquals(100000000, SweBank.getBalance("Kseniia").intValue());
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException { //fail
		SweBank.deposit("Bob", new Money(100000000, SEK));//an error occurred in Deposit() method
		assertEquals(100000000, SweBank.getBalance("Bob").intValue());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException { //fail
		SweBank.deposit("Bob", new Money(100000000, SEK));
		SweBank.withdraw("Bob", new Money(100000000, SEK));//an error occurred in Withdraw() method
		assertEquals(0, SweBank.getBalance("Bob").intValue());
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(100000000, SEK));
		assertEquals(100000000, SweBank.getBalance("Bob").intValue());
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		//between 2 accounts in different banks
		SweBank.deposit("Bob", new Money(100000000, SEK));
		SweBank.transfer("Bob", Nordea, "Bob", new Money(100000000, SEK));

		assertEquals(0, SweBank.getBalance("Bob").intValue());
		assertEquals(100000000, Nordea.getBalance("Bob").intValue());

		//between 2 accounts in the same bank
		SweBank.deposit("Bob", new Money(100000000, SEK));
		SweBank.transfer("Bob", "Ulrika", new Money(100000000, SEK));
		assertEquals(0, SweBank.getBalance("Bob").intValue()); //fail
		//an error occurred in method Transfer (), fixed Transfer between 2 same accounts
		assertEquals(100000000, SweBank.getBalance("Ulrika").intValue());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(100000000, SEK));
		SweBank.addTimedPayment("Bob", "1", 10, 0, new Money(100000000, SEK), SweBank, "Ulrika");
		SweBank.tick();
		assertEquals(0, SweBank.getBalance("Bob").intValue());
	}
}
