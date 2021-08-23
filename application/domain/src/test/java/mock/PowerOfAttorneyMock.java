package mock;

import nl.rabobank.authorization.usecase.Authorization;
import nl.rabobank.authorization.usecase.PowerOfAttorney;

public final class PowerOfAttorneyMock {

  public static PowerOfAttorney getOne() {
    return PowerOfAttorney.builder()
      .grantorDocument("432141")
      .granteeDocument("123412")
      .account(AccountMock.getOneSavingsAccount())
      .authorization(Authorization.READ)
      .build();
  }

}
