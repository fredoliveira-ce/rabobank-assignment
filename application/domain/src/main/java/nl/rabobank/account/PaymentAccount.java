package nl.rabobank.account;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentAccount implements Account {

  String number;
  String holderName;
  String holderDocument;
  Double balance;

  @Override
  public AccountType getType() {
    return AccountType.PAYMENT_ACCOUNT;
  }
}
