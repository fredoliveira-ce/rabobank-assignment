package nl.rabobank.account.usecase;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.exception.AccountWithoutRegisterException;
import nl.rabobank.user.usecase.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountDao dao;
  private final UserService userService;

  public Account save(final Account account) {
    return dao.save(account);
  }

  public void validate(final String holderDocument) {
    if (userService.findByDocument(holderDocument).isEmpty()) {
      throw new AccountWithoutRegisterException(holderDocument);
    }
  }
}
