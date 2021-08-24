package nl.rabobank.template;

import nl.rabobank.account.usecase.AccountType;
import nl.rabobank.api.rest.authorization.PowerOfAttorneyRequest;
import nl.rabobank.authorization.usecase.Authorization;

public final class PowerOfAttorneyRequestTemplate {

  private PowerOfAttorneyRequestTemplate() {}

  public static PowerOfAttorneyRequest getOneSavingsAccount() {
    return PowerOfAttorneyRequest.builder()
      .granteeDocument("703350247705921")
      .accountType(AccountType.SAVINGS_ACCOUNT.name())
      .authorization(Authorization.READ.name())
      .build();
  }

}
