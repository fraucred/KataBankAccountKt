import java.util.Date

data class BankClient(
    val accountHistory: AccountHistory = AccountHistory()
) {
    fun deposit(amount: Int): BankClient = BankClient(
        accountHistory = accountHistory.deposit(amount)
    )

    fun withdraw(amount: Int): BankClient = BankClient(
        accountHistory = accountHistory.withdraw(amount)
    )

    fun accountHistory(): AccountHistory = accountHistory
}

data class AccountHistory(
    val statements: LinkedHashSet<AccountHistoryStatement> = linkedSetOf(AccountHistoryStatement())
) {
    fun deposit(amount: Int): AccountHistory = AccountHistory(
        statements = LinkedHashSet(statements).apply {
            add(AccountHistoryStatement(balance = statements.last().balance).deposit(amount))
        }
    )

    fun withdraw(amount: Int): AccountHistory = AccountHistory(
        statements = LinkedHashSet(statements).apply {
            add(AccountHistoryStatement(balance = statements.last().balance).withdraw(amount))
        }
    )
}

data class AccountBalance(
    val amount: Int
) {
    fun addAmount(amount: Int): AccountBalance {
        if (amount < 0) {
            return this
        }
        return AccountBalance(this.amount + amount)
    }

    fun subtractAmount(amount: Int): AccountBalance {
        if (amount < 0 || this.amount - amount < 0) {
            return this
        }
        return AccountBalance(this.amount - amount)
    }
}

enum class Operation {
    DEPOSIT,
    WITHDRAW;

    fun execute(balance: AccountBalance, amount: Int): AccountHistoryStatement {
        if (this == DEPOSIT && balance.addAmount(amount) != balance) {
            return AccountHistoryStatement(balance = balance.addAmount(amount), operation = this, operationDate = Date())
        }
        if (this == WITHDRAW && balance.subtractAmount(amount) != balance) {
            return AccountHistoryStatement(balance = balance.subtractAmount(amount), operation = this, operationDate = Date())
        }
        return AccountHistoryStatement(balance = balance, operation = null, operationDate = null)
    }
}

data class AccountHistoryStatement(
    val balance: AccountBalance = AccountBalance(0),
    val operation: Operation? = null,
    val operationDate: Date? = null
) {
    fun deposit(amount: Int): AccountHistoryStatement = Operation.DEPOSIT.execute(balance, amount)
    fun withdraw(amount: Int): AccountHistoryStatement = Operation.WITHDRAW.execute(balance, amount)
}