package nl.rabobank.api.rest.authorization;

import lombok.Builder;
import lombok.Data;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.authorization.usecase.Authorization;
import nl.rabobank.authorization.usecase.PowerOfAttorney;

@Data
@Builder
public class PowerOfAttorneyResponse {

  private String grantorDocument;
  private Account account;
  private Authorization authorization;

  public static PowerOfAttorneyResponse from(PowerOfAttorney powerOfAttorney) {
    return PowerOfAttorneyResponse.builder()
      .grantorDocument(powerOfAttorney.getGrantorDocument())
      .account(powerOfAttorney.getAccount())
      .authorization(powerOfAttorney.getAuthorization())
      .build();
  }
}
