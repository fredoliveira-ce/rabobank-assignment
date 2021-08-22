package nl.rabobank.account.usecase;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SavingsAccount implements Account {

  String number;
  String holderName;
  String holderDocument;
  Double balance;

  @Override
  public AccountType getType() {
    return AccountType.SAVINGS_ACCOUNT;
  }
}
