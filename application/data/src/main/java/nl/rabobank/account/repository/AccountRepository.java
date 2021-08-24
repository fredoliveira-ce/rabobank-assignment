package nl.rabobank.account.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.repository.strategy.AccountStrategy;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.usecase.AccountType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
@RequiredArgsConstructor
public class AccountRepository implements AccountDao {

  private final AccountStrategy strategy;

  @Override
  public Account save(final Account account) {
    return strategy.getInstance(account.getType())
      .save(account);
  }

  @Override
  public Optional<Account> findBy(final String document, final AccountType type) {
    return strategy.getInstance(type)
      .findBy(document, type);
  }

}
