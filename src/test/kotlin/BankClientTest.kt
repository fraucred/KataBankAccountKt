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
}