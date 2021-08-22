package nl.rabobank.authorization;

import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import nl.rabobank.account.Account;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.exception.AccountNotFoundException;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerOfAttorneyService {

  private final PowerOfAttorneyDao dao;
  private final AccountDao accountDao;

  @SentrySpan
  public PowerOfAttorney save(PowerOfAttorney powerOfAttorney) {
    return accountDao.findAccount(powerOfAttorney.getAccount())
      .map(account -> dao.save(powerOfAttorney.toBuilder().account(account).build()))
      .orElseThrow(() -> new AccountNotFoundException(powerOfAttorney.getAccount().getNumber()));
  }

  @SentrySpan
  public List<Account> find(String user) {
    return dao.find(user);
  }
}
