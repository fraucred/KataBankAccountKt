import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class BankClientTest {

    @Test
    fun shouldDepositMoneyInEmptyBankAccount() {
        // GIVEN
        val bankClient = BankClient(0)

        // WHEN
        bankClient.deposit(1)

        // THEN
        assertEquals(1, bankClient.balance)
    }
}