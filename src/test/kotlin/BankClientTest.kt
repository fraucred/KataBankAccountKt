import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class BankClientTest {

    @Nested
    inner class Deposit {

        @Test
        fun shouldDepositMoneyInEmptyBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountHistoryStatement())

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement(AccountBalance(1), Operation.DEPOSIT)), bankClientWithOneInBalance)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithOneInBalance() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountHistoryStatement(balance = AccountBalance(1)))

            // WHEN
            val bankClientWithTwoInBalance = bankClientWithOneInBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement(AccountBalance(2), Operation.DEPOSIT)), bankClientWithTwoInBalance)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountHistoryStatement(balance = AccountBalance(2)))

            // WHEN
            val bankClientWithThreeInBalance = bankClientWithTwoInBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement(AccountBalance(3), Operation.DEPOSIT)), bankClientWithThreeInBalance)
        }

        @Test
        fun shouldNotDepositNegativeMoneyInBankAccount() {
            // GIVEN
            val newBankClient = BankClient(AccountHistoryStatement())

            // WHEN
            val bankClientAfterDeposit = newBankClient.deposit(-1)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement()), bankClientAfterDeposit)
        }

        @Test
        fun shouldDepositMoneyInBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(AccountHistoryStatement(balance = AccountBalance(-1)))

            // WHEN
            val bankClientAfterDeposit = bankClientWithNegativeBalance.deposit(1)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement(AccountBalance(0) ,Operation.DEPOSIT)), bankClientAfterDeposit)
        }
    }

    @Nested
    inner class Withdraw {

        @Test
        fun shouldWithdrawMoneyFromNotEmptyBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountHistoryStatement())

            // WHEN
            val bankClientWithZeroInBalance = bankClientWithOneInBalance.withdraw(1)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement()), bankClientWithZeroInBalance)
        }

        @Test
        fun shouldWithdrawSomeMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountHistoryStatement())

            // WHEN
            val bankClientWithOneInBalance = bankClientWithTwoInBalance.withdraw(1)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement()), bankClientWithOneInBalance)
        }

        @Test
        fun shouldWithdrawAllMoneyFromBankAccountWithTwoInBalance() {
            // GIVEN
            val bankClientWithTwoInBalance = BankClient(AccountHistoryStatement())

            // WHEN
            val bankClientWithEmptyBalance = bankClientWithTwoInBalance.withdraw(2)

            // THEN
            assertEquals(BankClient(AccountHistoryStatement()), bankClientWithEmptyBalance)
        }

        @Test
        fun shouldNotWithdrawSomeMoneyFromBankAccountWithNegativeBalance() {
            // GIVEN
            val bankClientWithNegativeBalance = BankClient(AccountHistoryStatement())

            // WHEN
            val bankClientAfterWithdraw = bankClientWithNegativeBalance.withdraw(2)

            // THEN
            assertEquals(bankClientWithNegativeBalance, bankClientAfterWithdraw)
        }

        @Test
        fun shouldNotWithdrawNegativeMoneyFromBankAccount() {
            // GIVEN
            val bankClientWithOneInBalance = BankClient(AccountHistoryStatement())

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
            val newBankClient = BankClient(AccountHistoryStatement())

            // WHEN
            val bankClientWithOneInBalance = newBankClient.deposit(1)

            // THEN
            assertEquals(AccountHistoryStatement(AccountBalance(1), Operation.DEPOSIT), bankClientWithOneInBalance.accountHistoryStatement())
        }

        @Test
        fun shouldReturnAccountHistoryStatementWithSingleWithdrawOperation() {
            // GIVEN
            val newBankClient = BankClient(AccountHistoryStatement(balance = AccountBalance(1)))

            // WHEN
            val bankClientWithEmptyBalance = newBankClient.withdraw(1)

            // THEN
            assertEquals(AccountHistoryStatement(AccountBalance(0), Operation.WITHDRAW), bankClientWithEmptyBalance.accountHistoryStatement())
        }
    }
}