data class BankClient(
    val balance: AccountBalance
) {
    fun deposit(amount: Int) : BankClient = BankClient(balance.addAmount(amount))
    fun withdraw(amount: Int) {

    }
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
}