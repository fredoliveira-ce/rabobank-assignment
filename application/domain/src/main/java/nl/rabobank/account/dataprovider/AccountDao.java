package nl.rabobank.account.dataprovider;

import nl.rabobank.account.Account;

import java.util.Optional;

public interface AccountDao {

  Account save(Account account);

  Optional<Account> findAccount(Account account);

}
