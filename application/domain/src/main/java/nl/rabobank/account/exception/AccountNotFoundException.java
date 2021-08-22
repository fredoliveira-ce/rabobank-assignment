package nl.rabobank.account.exception;

import nl.rabobank.account.usecase.AccountType;
import nl.rabobank.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends NotFoundException {

  public AccountNotFoundException(AccountType type, String number) {
    super(String.format("Account with type %s and number '%s' not found.", type.name(), number));
  }
}