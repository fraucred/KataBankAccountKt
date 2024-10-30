data class BankClient(
    val balance: AccountBalance,
    val accountHistory: AccountHistoryStatement = AccountHistoryStatement(operation = null)
) {
    fun deposit(amount: Int): BankClient = BankClient(
        balance = balance.addAmount(amount),
        accountHistory = accountHistory.deposit(amount)
    )

    fun withdraw(amount: Int): BankClient = BankClient(
        balance = balance.subtractAmount(amount),
        accountHistory = accountHistory.withdraw(amount)
    )

    fun accountHistoryStatement(): AccountHistoryStatement = accountHistory
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

data class AccountHistoryStatement(
    val balance: AccountBalance = AccountBalance(0),
    val operation: Operation? = null
) {
    fun deposit(amount: Int): AccountHistoryStatement = Operation.DEPOSIT.execute(balance, amount)
    fun withdraw(amount: Int): AccountHistoryStatement = Operation.WITHDRAW.execute(balance, amount)
}

enum class Operation {
    DEPOSIT,
    WITHDRAW;

    fun execute(balance: AccountBalance, amount: Int): AccountHistoryStatement {
        if (this == DEPOSIT && balance.addAmount(amount) != balance) {
            return AccountHistoryStatement(balance = balance.addAmount(amount), operation = this)
        }
        if (this == WITHDRAW && balance.subtractAmount(amount) != balance) {
            return AccountHistoryStatement(balance = balance.subtractAmount(amount), operation = this)
        }
        return AccountHistoryStatement(balance = balance, operation = null)
    }
}