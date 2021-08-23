package mock;

import nl.rabobank.authorization.usecase.Authorization;
import nl.rabobank.authorization.usecase.PowerOfAttorney;

public final class PowerOfAttorneyMock {

  public static PowerOfAttorney getOne() {
    return PowerOfAttorney.builder()
      .grantorDocument("12341234")
      .granteeDocument("")
      .account(AccountMock.getOneSavingsAccount())
      .authorization(Authorization.READ)
      .build();
  }

}
