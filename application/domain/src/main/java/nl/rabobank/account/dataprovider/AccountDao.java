package nl.rabobank.account.dataprovider;

import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.usecase.AccountType;

import java.util.Optional;

public interface AccountDao {

  Account save(Account account);

  Optional<Account> findBy(String document, AccountType type);

}
