package nl.rabobank.authorization;

import lombok.Builder;
import lombok.Value;
import nl.rabobank.account.Account;

@Value
@Builder(toBuilder = true)
public class PowerOfAttorney {

  String grantorName;
  String granteeName;
  Account account;
  Authorization authorization;

}
