data class BankClient(
    val balance: AccountBalance
) {
    fun deposit(amount: Int) : BankClient = BankClient(balance.addAmount(amount))
    fun withdraw(amount: Int) : BankClient = BankClient(balance.substractAmount(amount))
    fun accountHistoryStatement(): AccountHistoryStatement = AccountHistoryStatement(
        operation = null
    )
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

    fun substractAmount(amount: Int): AccountBalance {
        if (amount < 0 || this.amount - amount < 0) {
            return this
        }
        return AccountBalance(this.amount - amount)
    }
}

data class AccountHistoryStatement(
    val operation: Operation?
) {

}

enum class Operation {
    DEPOSIT
}