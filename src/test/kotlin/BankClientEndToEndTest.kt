import io.mockk.every
import io.mockk.mockkObject
import time.Time
import java.time.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BankClientEndToEndTest {

    @BeforeTest
    fun setUp() {
        mockkObject(Time)
        every { Time.getCurrentTime() } returnsMany listOf(
            LocalDateTime.of(2024, 1, 1, 12, 33),
            LocalDateTime.of(2024, 1, 2, 12, 33),
            LocalDateTime.of(2024, 1, 3, 12, 33),
            LocalDateTime.of(2024, 1, 4, 12, 33),
            LocalDateTime.of(2024, 1, 5, 12, 33),
            LocalDateTime.of(2024, 1, 6, 12, 33)
        )
    }

    @Test
    fun `should apply deposit and withdrawal operations on a bank account and see history`() {
        // GIVEN
        val bankClient = BankClient()

        // WHEN
        val emptyBalanceBankClient = bankClient
            .deposit(100)
            .withdraw(50)
            .deposit(200)
            .withdraw(400)
            .deposit(150)
            .withdraw(400)

        // THEN
        assertEquals(
            "DEPOSIT ",
            emptyBalanceBankClient.accountHistory.formattedAccountHistory()
        )
    }
}