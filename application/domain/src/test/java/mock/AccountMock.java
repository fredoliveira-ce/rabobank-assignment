package mock;

import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.usecase.PaymentAccount;
import nl.rabobank.account.usecase.SavingsAccount;

public final class AccountMock {

  public static Account getOneSavingsAccount() {
    return SavingsAccount.builder()
      .holderName("Foo")
      .holderDocument("1234567890")
      .balance(5234526.98)
      .number("1111")
      .build();
  }

  public static Account getOnePaymentAccount() {
    return PaymentAccount.builder()
      .holderName("Bar")
      .holderDocument("0987654321")
      .balance(14526.98)
      .number("2222")
      .build();
  }

}
