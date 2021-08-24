package nl.rabobank.template;

import nl.rabobank.api.rest.account.AccountRequest;

public final class AccountRequestTemplate {

  public static AccountRequest getOne() {
    return AccountRequest.builder()
      .holderName("Holder test")
      .holderDocument("000111222")
      .balance(23847.32)
      .number("4444")
      .type("SAVINGS_ACCOUNT")
      .build();
  }
}
