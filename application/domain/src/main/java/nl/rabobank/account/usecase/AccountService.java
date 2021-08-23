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

  public Account save(Account account) {
    return dao.save(account);
  }

  public void validate(String holderDocument) {
    userService.findByDocument(holderDocument)
      .orElseThrow(() -> new AccountWithoutRegisterException(holderDocument));
  }
}
