import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class BankClientTest {

    @Nested
    inner class Deposit {

        @Test
        fun shouldDepositMoneyInEmptyBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountBalance(0), AccountHistoryStatement(null))

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(1), AccountHistoryStatement(Operation.DEPOSIT)), bankClientWithOneInBalance)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithOneInBalance() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountBalance(1), AccountHistoryStatement(null))

            // WHEN
            val bankClientWithTwoInBalance = bankClientWithOneInBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(2), AccountHistoryStatement(Operation.DEPOSIT)), bankClientWithTwoInBalance)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountBalance(2), AccountHistoryStatement(null))

            // WHEN
            val bankClientWithThreeInBalance = bankClientWithTwoInBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(3), AccountHistoryStatement(Operation.DEPOSIT)), bankClientWithThreeInBalance)
        }

        @Test
        fun shouldNotDepositNegativeMoneyInBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountBalance(0), AccountHistoryStatement(null))

            // WHEN
            val bankClientAfterDeposit = newBankClient.deposit(-1)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement(null)), bankClientAfterDeposit)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(AccountBalance(-1), AccountHistoryStatement(null))

            // WHEN
            val bankClientAfterDeposit = bankClientWithNegativeBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement(Operation.DEPOSIT)), bankClientAfterDeposit)
        }
    }

    @Nested
    inner class Withdraw {

        @Test
        fun shouldWithdrawMoneyFromNotEmptyBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountBalance(1), AccountHistoryStatement(null))

            // WHEN
            val bankClientWithZeroInBalance = bankClientWithOneInBalance.withdraw(1)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement(null)), bankClientWithZeroInBalance)
        }

        @Test
        fun shouldWithdrawSomeMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountBalance(2), AccountHistoryStatement(null))

            // WHEN
            val bankClientWithOneInBalance = bankClientWithTwoInBalance.withdraw(1)

            // THEN
            assertEquals(BankClient(AccountBalance(1), AccountHistoryStatement(null)), bankClientWithOneInBalance)
        }

        @Test
        fun shouldWithdrawAllMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountBalance(2), AccountHistoryStatement(null))

            // WHEN
            val bankClientWithEmptyBalance = bankClientWithTwoInBalance.withdraw(2)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement(null)), bankClientWithEmptyBalance)
        }

        @Test
        fun shouldNotWithdrawSomeMoneyFromBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(AccountBalance(-1), AccountHistoryStatement(null))

            // WHEN
            val bankClientAfterWithdraw = bankClientWithNegativeBalance.withdraw(2)

            // THEN
            assertEquals(bankClientWithNegativeBalance, bankClientAfterWithdraw)
        }

        @Test
        fun shouldNotWithdrawNegativeMoneyFromBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountBalance(1), AccountHistoryStatement(null))

            // WHEN
            val bankClientAfterWithdraw = bankClientWithOneInBalance.withdraw(-1)

            // THEN
            assertEquals(bankClientWithOneInBalance, bankClientAfterWithdraw)
        }
    }

    @Nested
    inner class AccountHistoryStatement {

        @Test
        fun shouldReturnAccountHistoryStatementWithSingleDepositOperation() {
            // GIVEN
            val newBankClient = BankClient(AccountBalance(0), AccountHistoryStatement(null))

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(AccountHistoryStatement(Operation.DEPOSIT), bankClientWithOneInBalance.accountHistoryStatement())
        }
    }
}