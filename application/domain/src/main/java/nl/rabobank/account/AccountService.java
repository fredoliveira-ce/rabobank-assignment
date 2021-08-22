package nl.rabobank.account;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.authorization.PowerOfAttorney;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountDao dao;

  public Account save(Account account) {
    return dao.save(account);
  }

}
