package nl.rabobank.api.rest.authorization;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.authorization.usecase.Authorization;
import nl.rabobank.authorization.usecase.PowerOfAttorney;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PowerOfAttorneyResponse {

  private String grantorDocument;
  private String granteeDocument;
  private Account account;
  private Authorization authorization;

  public static PowerOfAttorneyResponse from(final PowerOfAttorney powerOfAttorney) {
    return PowerOfAttorneyResponse.builder()
      .grantorDocument(powerOfAttorney.getGrantorDocument())
      .granteeDocument(powerOfAttorney.getGranteeDocument())
      .account(powerOfAttorney.getAccount())
      .authorization(powerOfAttorney.getAuthorization())
      .build();
  }
}
