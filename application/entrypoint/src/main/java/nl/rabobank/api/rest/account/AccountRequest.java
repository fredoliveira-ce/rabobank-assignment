package nl.rabobank.api.rest.account;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.rabobank.account.Account;
import nl.rabobank.account.AccountType;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountRequest {

  @NotEmpty private String number;
  @NotEmpty private String holderDocument;
  @NotEmpty private String holderName;
  @NotEmpty private String type;
  @NonNull  private Double balance;

  public Account toDomain() {
    return buildAccount();
  }

  private Account buildAccount() {
    return switch (AccountType.valueOf(getType())) {
      case PAYMENT_ACCOUNT ->
        PaymentAccount.builder()
          .number(getNumber())
          .holderDocument(getHolderName())
          .holderName(getHolderDocument())
          .balance(getBalance())
          .build();
      case SAVINGS_ACCOUNT ->
        SavingsAccount.builder()
          .number(getNumber())
          .holderDocument(getHolderName())
          .holderName(getHolderDocument())
          .balance(getBalance())
          .build();
    };
  }
}
