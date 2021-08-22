package nl.rabobank.api.rest.authorization;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.usecase.AccountType;
import nl.rabobank.account.usecase.PaymentAccount;
import nl.rabobank.account.usecase.SavingsAccount;
import nl.rabobank.authorization.usecase.Authorization;
import nl.rabobank.authorization.usecase.PowerOfAttorney;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PowerOfAttorneyRequest {

  @NotEmpty private String grantorName;
  @NotEmpty private String granteeName;
  @NotEmpty private String accountNumber;
  @NotEmpty private String accountType;
  @NotEmpty private String authorization;

  public PowerOfAttorney toDomain() {
    return PowerOfAttorney.builder()
      .grantorName(getGrantorName())
      .granteeName(getGranteeName())
      .authorization(Authorization.valueOf(getAuthorization()))
      .account(buildAccount())
      .build();
  }

  private Account buildAccount() {
    return switch (AccountType.valueOf(getAccountType())) {
      case PAYMENT_ACCOUNT ->
        PaymentAccount.builder()
          .number(getAccountNumber())
          .build();
      case SAVINGS_ACCOUNT ->
        SavingsAccount.builder()
          .number(getAccountNumber())
          .build();
    };
  }
}
