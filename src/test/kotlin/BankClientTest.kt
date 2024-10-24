import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class BankClientTest {

    @Test
    fun shouldDepositMoneyInEmptyBankAccount() {
        // GIVEN
        val newBankClient = BankClient(AccountBalance(0))

        // WHEN
        val bankClientWithOneInBalance = newBankClient.deposit(1)

        // THEN
        assertEquals(BankClient(AccountBalance(1)), bankClientWithOneInBalance)
    }

    @Test
    fun shouldDepositMoneyInBankAccountWithOneInBalance() {
        // GIVEN
        val bankClientWithOneInBalance = BankClient(AccountBalance(1))

        // WHEN
        val bankClientWithTwoInBalance = bankClientWithOneInBalance.deposit(1)

        // THEN
        assertEquals(BankClient(AccountBalance(2)), bankClientWithTwoInBalance)
    }

    @Test
    fun shouldDepositMoneyInBankAccountWithTwoInBalance() {
        // GIVEN
        val bankClientWithTwoInBalance = BankClient(AccountBalance(2))

        // WHEN
        val bankClientWithThreeInBalance = bankClientWithTwoInBalance.deposit(1)

        // THEN
        assertEquals(BankClient(AccountBalance(3)), bankClientWithThreeInBalance)
    }

    @Test
    fun shouldNotDepositNegativeMoneyInBankAccount() {
        // GIVEN
        val newBankClient = BankClient(AccountBalance(0))

        // WHEN
        val bankClientAfterDeposit = newBankClient.deposit(-1)

        // THEN
        assertEquals(BankClient(AccountBalance(0)), bankClientAfterDeposit)
    }

    @Test
    fun shouldDepositMoneyInBankAccountWithNegativeBalance() {
        // GIVEN
        val bankClientWithNegativeBalance = BankClient(AccountBalance(-1))

        // WHEN
        val bankClientAfterDeposit = bankClientWithNegativeBalance.deposit(1)

        // THEN
        assertEquals(BankClient(AccountBalance(0)), bankClientAfterDeposit)
    }
}