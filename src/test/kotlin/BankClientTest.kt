import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class BankClientTest {

    @Nested
    inner class Deposit {

        @Test
        fun shouldDepositMoneyInEmptyBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountBalance(0), AccountHistoryStatement())

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(1), AccountHistoryStatement(AccountBalance(1), Operation.DEPOSIT)), bankClientWithOneInBalance)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithOneInBalance() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountBalance(1), AccountHistoryStatement(balance = AccountBalance(1)))

            // WHEN
            val bankClientWithTwoInBalance = bankClientWithOneInBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(2), AccountHistoryStatement(AccountBalance(2), Operation.DEPOSIT)), bankClientWithTwoInBalance)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountBalance(2), AccountHistoryStatement(balance = AccountBalance(2)))

            // WHEN
            val bankClientWithThreeInBalance = bankClientWithTwoInBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(3), AccountHistoryStatement(AccountBalance(3), Operation.DEPOSIT)), bankClientWithThreeInBalance)
        }

        @Test
        fun shouldNotDepositNegativeMoneyInBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountBalance(0), AccountHistoryStatement())

            // WHEN
            val bankClientAfterDeposit = newBankClient.deposit(-1)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement()), bankClientAfterDeposit)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(AccountBalance(-1), AccountHistoryStatement(balance = AccountBalance(-1)))

            // WHEN
            val bankClientAfterDeposit = bankClientWithNegativeBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement(AccountBalance(0) ,Operation.DEPOSIT)), bankClientAfterDeposit)
        }
    }

    @Nested
    inner class Withdraw {

        @Test
        fun shouldWithdrawMoneyFromNotEmptyBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountBalance(1), AccountHistoryStatement())

            // WHEN
            val bankClientWithZeroInBalance = bankClientWithOneInBalance.withdraw(1)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement()), bankClientWithZeroInBalance)
        }

        @Test
        fun shouldWithdrawSomeMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountBalance(2), AccountHistoryStatement())

            // WHEN
            val bankClientWithOneInBalance = bankClientWithTwoInBalance.withdraw(1)

            // THEN
            assertEquals(BankClient(AccountBalance(1), AccountHistoryStatement()), bankClientWithOneInBalance)
        }

        @Test
        fun shouldWithdrawAllMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountBalance(2), AccountHistoryStatement())

            // WHEN
            val bankClientWithEmptyBalance = bankClientWithTwoInBalance.withdraw(2)

            // THEN
            assertEquals(BankClient(AccountBalance(0), AccountHistoryStatement()), bankClientWithEmptyBalance)
        }

        @Test
        fun shouldNotWithdrawSomeMoneyFromBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(AccountBalance(-1), AccountHistoryStatement())

            // WHEN
            val bankClientAfterWithdraw = bankClientWithNegativeBalance.withdraw(2)

            // THEN
            assertEquals(bankClientWithNegativeBalance, bankClientAfterWithdraw)
        }

        @Test
        fun shouldNotWithdrawNegativeMoneyFromBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountBalance(1), AccountHistoryStatement())

            // WHEN
            val bankClientAfterWithdraw = bankClientWithOneInBalance.withdraw(-1)

            // THEN
            assertEquals(bankClientWithOneInBalance, bankClientAfterWithdraw)
        }
    }

    @Nested
    inner class BankAccountHistoryStatement {

        @Test
        fun shouldReturnAccountHistoryStatementWithSingleDepositOperation() {
            // GIVEN
            val newBankClient = BankClient(AccountBalance(0), AccountHistoryStatement())

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(AccountHistoryStatement(AccountBalance(1), Operation.DEPOSIT), bankClientWithOneInBalance.accountHistoryStatement())
        }
    }
}