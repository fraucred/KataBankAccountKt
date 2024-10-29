data class BankClient(
    val balance: AccountBalance,
    val accountHistory: AccountHistoryStatement = AccountHistoryStatement(null)
) {
    fun deposit(amount: Int) : BankClient = BankClient(balance.addAmount(amount), AccountHistoryStatement(Operation.DEPOSIT))
    fun withdraw(amount: Int) : BankClient = BankClient(balance.subtractAmount(amount), accountHistory)
    fun accountHistoryStatement(): AccountHistoryStatement = accountHistory
}

data class AccountBalance(
    val amount: Int
) {
    fun addAmount(amount: Int) : AccountBalance {
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
    val operation: Operation?
)

enum class Operation {
    DEPOSIT
}