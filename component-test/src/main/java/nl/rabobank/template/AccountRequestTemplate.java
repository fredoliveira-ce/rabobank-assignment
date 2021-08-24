package nl.rabobank.template;

import nl.rabobank.api.rest.account.AccountRequest;

import static nl.rabobank.account.usecase.AccountType.SAVINGS_ACCOUNT;

public final class AccountRequestTemplate {

  private AccountRequestTemplate() {}

  public static AccountRequest getOneSavingsAccount() {
    return AccountRequest.builder()
      .holderName("Holder test")
      .holderDocument("000111222")
      .balance(23847.32)
      .number("4444")
      .type(SAVINGS_ACCOUNT.name())
      .build();
  }

  public static AccountRequest getOneSavingsAccount(String document) {
    return AccountRequest.builder()
      .holderName("Holder test")
      .holderDocument(document)
      .balance(23847.32)
      .number("4444")
      .type(SAVINGS_ACCOUNT.name())
      .build();
  }
}
