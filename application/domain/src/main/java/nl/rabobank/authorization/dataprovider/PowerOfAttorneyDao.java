package nl.rabobank.authorization.dataprovider;

import nl.rabobank.account.Account;
import nl.rabobank.authorization.PowerOfAttorney;

import java.util.List;

public interface PowerOfAttorneyDao {

  PowerOfAttorney save(PowerOfAttorney powerOfAttorney);

  List<Account> find(String user);

}
