package nl.rabobank.api.rest.account;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import nl.rabobank.account.usecase.Account;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountResponse {

  private String type;
  private String number;
  private String holderName;
  private String holderDocument;

  public static AccountResponse from(Account account) {
    return AccountResponse.builder()
      .holderDocument(account.getHolderDocument())
      .holderName(account.getHolderName())
      .number(account.getNumber())
      .type(account.getType().name())
      .build();
  }
}
