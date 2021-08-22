package nl.rabobank.authorization.usecase;

import lombok.Builder;
import lombok.Value;
import nl.rabobank.account.usecase.Account;

@Value
@Builder(toBuilder = true)
public class PowerOfAttorney {

  String grantorName;
  String granteeName;
  Account account;
  Authorization authorization;

}
