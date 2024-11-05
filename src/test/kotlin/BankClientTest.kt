import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import time.Time
import java.time.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class BankClientTest {

    @BeforeTest
    fun setUp() {
        mockkObject(Time)
        every { Time.getCurrentTime() } returns LocalDateTime.of(2024, 1, 1, 10, 26)
    }

    @Nested
    inner class Deposit {

        @Test
        fun shouldDepositMoneyInEmptyBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountHistory())

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(
                BankClient(
                    AccountHistory(
                        statements = linkedSetOf(
                            AccountHistoryStatement(),
                            AccountHistoryStatement(
                                balance = AccountBalance(1),
                                operation = Operation.DEPOSIT,
                                operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                                operationAmount = 1
                            )
                        )
                    )
                ),
                bankClientWithOneInBalance
            )
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithOneInBalance() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(
                AccountHistory(statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(1))))
            )

            // WHEN
            val bankClientWithTwoInBalance = bankClientWithOneInBalance.deposit(1)

            // THEN
            assertEquals(
                BankClient(
                    AccountHistory(
                        statements = linkedSetOf(
                            AccountHistoryStatement(balance = AccountBalance(1)),
                            AccountHistoryStatement(
                                balance = AccountBalance(2),
                                operation = Operation.DEPOSIT,
                                operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                                operationAmount = 1
                            )
                        )
                    )
                ),
                bankClientWithTwoInBalance
            )
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(
                AccountHistory(statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(2))))
            )

            // WHEN
            val bankClientWithThreeInBalance = bankClientWithTwoInBalance.deposit(1)

            // THEN
            assertEquals(
                BankClient(
                    AccountHistory(
                        statements = linkedSetOf(
                            AccountHistoryStatement(balance = AccountBalance(2)),
                            AccountHistoryStatement(
                                balance = AccountBalance(3),
                                operation = Operation.DEPOSIT,
                                operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                                operationAmount = 1
                            )
                        )
                    )
                ),
                bankClientWithThreeInBalance
            )
        }

        @Test
        fun shouldNotDepositNegativeMoneyInBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountHistory())

            // WHEN
            val bankClientAfterDeposit = newBankClient.deposit(-1)

            // THEN
            assertEquals(BankClient(AccountHistory()), bankClientAfterDeposit)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(
                AccountHistory(statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(-1))))
            )

            // WHEN
            val bankClientAfterDeposit = bankClientWithNegativeBalance.deposit(1)

            // THEN
            assertEquals(
                BankClient(
                    AccountHistory(
                        statements = linkedSetOf(
                            AccountHistoryStatement(balance = AccountBalance(-1)),
                            AccountHistoryStatement(
                                balance = AccountBalance(0),
                                operation = Operation.DEPOSIT,
                                operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                                operationAmount = 1
                            )
                        )
                    )
                ),
                bankClientAfterDeposit
            )
        }
    }

    @Nested
    inner class Withdraw {

        @Test
        fun shouldWithdrawMoneyFromNotEmptyBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountHistory(
                statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(1)))
            ))

            // WHEN
            val bankClientWithZeroInBalance = bankClientWithOneInBalance.withdraw(1)

            // THEN
            assertEquals(
                BankClient(
                    AccountHistory(
                        statements = linkedSetOf(
                            AccountHistoryStatement(balance = AccountBalance(1)),
                            AccountHistoryStatement(
                                balance = AccountBalance(0),
                                operation = Operation.WITHDRAW,
                                operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                                operationAmount = 1
                            )
                        )
                    )
                ),
                bankClientWithZeroInBalance
            )
        }

        @Test
        fun shouldWithdrawSomeMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(
                AccountHistory(statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(2))))
            )

            // WHEN
            val bankClientWithOneInBalance = bankClientWithTwoInBalance.withdraw(1)

            // THEN
            assertEquals(
                BankClient(
                    AccountHistory(
                        statements = linkedSetOf(
                            AccountHistoryStatement(balance = AccountBalance(2)),
                            AccountHistoryStatement(
                                balance = AccountBalance(1),
                                operation = Operation.WITHDRAW,
                                operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                                operationAmount = 1
                            )
                        )
                    )
                ),
                bankClientWithOneInBalance
            )
        }

        @Test
        fun shouldWithdrawAllMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountHistory(
                statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(2)))
            ))

            // WHEN
            val bankClientWithEmptyBalance = bankClientWithTwoInBalance.withdraw(2)

            // THEN
            assertEquals(
                BankClient(
                    AccountHistory(
                        statements = linkedSetOf(
                            AccountHistoryStatement(balance = AccountBalance(2)),
                            AccountHistoryStatement(
                                balance = AccountBalance(0),
                                operation = Operation.WITHDRAW,
                                operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                                operationAmount = 2
                            )
                        )
                    )
                ),
                bankClientWithEmptyBalance
            )
        }

        @Test
        fun shouldNotWithdrawSomeMoneyFromBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(AccountHistory(
                statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(-1)))
            ))

            // WHEN
            val bankClientAfterWithdraw = bankClientWithNegativeBalance.withdraw(2)

            // THEN
            assertEquals(bankClientWithNegativeBalance, bankClientAfterWithdraw)
        }

        @Test
        fun shouldNotWithdrawNegativeMoneyFromBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountHistory(
                statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(1)))
            ))

            // WHEN
            val bankClientAfterWithdraw = bankClientWithOneInBalance.withdraw(-1)

            // THEN
            assertEquals(bankClientWithOneInBalance, bankClientAfterWithdraw)
        }
    }

    @Nested
    inner class BankAccountHistory {

        @Test
        fun shouldReturnAccountHistoryStatementWithSingleDepositOperation() {
            // GIVEN
            val newBankClient = BankClient(AccountHistory())

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(
                AccountHistory(
                    statements = linkedSetOf(
                        AccountHistoryStatement(balance = AccountBalance(0)),
                        AccountHistoryStatement(
                            balance = AccountBalance(1),
                            operation = Operation.DEPOSIT,
                            operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                            operationAmount = 1
                        )
                    )
                ),
                bankClientWithOneInBalance.accountHistory
            )
        }

        @Test
        fun shouldReturnAccountHistoryStatementWithSingleWithdrawOperation() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(
                AccountHistory(statements = linkedSetOf(AccountHistoryStatement(balance = AccountBalance(1))))
            )

            // WHEN
            val bankClientWithEmptyBalance = bankClientWithOneInBalance.withdraw(1)

            // THEN
            assertEquals(
                AccountHistory(
                    statements = linkedSetOf(
                        AccountHistoryStatement(balance = AccountBalance(1)),
                        AccountHistoryStatement(
                            balance = AccountBalance(0),
                            operation = Operation.WITHDRAW,
                            operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                            operationAmount = 1
                        )
                    )
                ),
                bankClientWithEmptyBalance.accountHistory
            )
        }

        @Test
        fun shouldReturnAccountHistoryStatementWithDepositThenWithdrawOperations() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(
                AccountHistory(
                    statements = linkedSetOf(
                        AccountHistoryStatement(balance = AccountBalance(1), operation = Operation.DEPOSIT)
                    )
                )
            )

            // WHEN
            val bankClientWithEmptyBalance = bankClientWithOneInBalance.withdraw(1)

            // THEN
            assertEquals(
                AccountHistory(
                    statements = linkedSetOf(
                        AccountHistoryStatement(balance = AccountBalance(1), operation = Operation.DEPOSIT),
                        AccountHistoryStatement(
                            balance = AccountBalance(0),
                            operation = Operation.WITHDRAW,
                            operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                            operationAmount = 1
                        )
                    )
                ),
                bankClientWithEmptyBalance.accountHistory
            )
        }

        @Test
        fun shouldReturnFormattedAccountHistory() {
            // GIVEN
            val bankClient = BankClient(
                AccountHistory(
                    statements = linkedSetOf(
                        AccountHistoryStatement(
                            balance = AccountBalance(100),
                        ),
                        AccountHistoryStatement(
                            balance = AccountBalance(101),
                            operation = Operation.DEPOSIT,
                            operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 26),
                            operationAmount = 1
                        ),
                        AccountHistoryStatement(
                            balance = AccountBalance(81),
                            operation = Operation.WITHDRAW,
                            operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 27),
                            operationAmount = 20
                        ),
                        AccountHistoryStatement(
                            balance = AccountBalance(96),
                            operation = Operation.DEPOSIT,
                            operationDateTime = LocalDateTime.of(2024, 1, 1, 10, 28),
                            operationAmount = 15
                        )
                    )
                )
            )

            // WHEN
            val formattedAccountHistory = bankClient.accountHistory.formattedAccountHistory()

            // THEN
            assertEquals(
                "DEPOSIT 1 at 2024-01-01T10:26 => new balance 101 ### WITHDRAW 20 at 2024-01-01T10:27 => new balance 81 ### DEPOSIT 15 at 2024-01-01T10:28 => new balance 96",
                formattedAccountHistory
            )
        }
    }
}