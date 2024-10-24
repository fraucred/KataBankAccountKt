data class BankClient(
    val balance: AccountBalance
) {
    fun deposit(amount: Int) : BankClient = BankClient(balance.addAmount(amount))
}

data class AccountBalance(
    val amount: Int
) {
    fun addAmount(amount: Int) : AccountBalance = AccountBalance(this.amount + amount)
}