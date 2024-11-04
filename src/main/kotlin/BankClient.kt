import time.Time
import java.time.LocalDateTime

data class BankClient(
    val accountHistory: AccountHistory = AccountHistory()
) {
    fun deposit(amount: Int): BankClient = BankClient(
        accountHistory = accountHistory.deposit(amount)
    )

    fun withdraw(amount: Int): BankClient = BankClient(
        accountHistory = accountHistory.withdraw(amount)
    )
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

    fun formattedAccountHistory(): String {
        return statements
            .filter { it.operation != null && it.operationDateTime != null }
            .map { "${it.operation} on ${it.operationDateTime} => new balance ${it.balance}" }
            .joinToString(separator = " ### ")
    }
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

    override fun toString(): String {
        return amount.toString()
    }
}

enum class Operation {
    DEPOSIT,
    WITHDRAW;

    fun execute(balance: AccountBalance, amount: Int): AccountHistoryStatement {
        if (this == DEPOSIT && balance.addAmount(amount) != balance) {
            return AccountHistoryStatement(balance = balance.addAmount(amount), operation = this, operationDateTime = Time.getCurrentTime())
        }
        if (this == WITHDRAW && balance.subtractAmount(amount) != balance) {
            return AccountHistoryStatement(balance = balance.subtractAmount(amount), operation = this, operationDateTime = Time.getCurrentTime())
        }
        return AccountHistoryStatement(balance = balance, operation = null, operationDateTime = null)
    }
}

data class AccountHistoryStatement(
    val balance: AccountBalance = AccountBalance(0),
    val operation: Operation? = null,
    val operationDateTime: LocalDateTime? = null
) {
    fun deposit(amount: Int): AccountHistoryStatement = Operation.DEPOSIT.execute(balance, amount)
    fun withdraw(amount: Int): AccountHistoryStatement = Operation.WITHDRAW.execute(balance, amount)
}