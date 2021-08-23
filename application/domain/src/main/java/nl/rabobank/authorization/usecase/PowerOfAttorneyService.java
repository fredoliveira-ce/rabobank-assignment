package nl.rabobank.authorization.usecase;

import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.exception.AccountNotFoundException;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import nl.rabobank.authorization.exception.SelfPowerOfAttorneyNotAllowedException;
import nl.rabobank.user.usecase.User;
import nl.rabobank.user.usecase.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerOfAttorneyService {

  private final PowerOfAttorneyDao dao;
  private final AccountDao accountDao;
  private final UserService userService;

  @SentrySpan
  public PowerOfAttorney save(PowerOfAttorney powerOfAttorney) {
    return accountDao.findAccountByDocument(
        powerOfAttorney.getGrantorDocument(), powerOfAttorney.getAccount().getType())
      .map(account -> dao.save(powerOfAttorney.toBuilder()
        .account(account)
        .build()))
      .orElseThrow(() -> new AccountNotFoundException(
        powerOfAttorney.getGrantorDocument(), powerOfAttorney.getAccount().getType()));
  }

  @SentrySpan
  public List<PowerOfAttorney> find(String username) {
    var user = userService.getUserDetails(username);
    return dao.findByGranteeDocument(user.getDocument());
  }

  public User validate(final String granteeDocument, final String username) {
    var user = userService.getUserDetails(username);

    if (granteeDocument.equals(user.getDocument())) {
      throw new SelfPowerOfAttorneyNotAllowedException(granteeDocument);
    }

    return user;
  }
}
