import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class BankClientTest {

    @Test
    fun shouldDepositMoneyInEmptyBankAccount() {
        // GIVEN
        val bankClient = BankClient(AccountBalance(0))

        // WHEN
        val bankClientWithOneInBalance = bankClient.deposit(1)

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
}