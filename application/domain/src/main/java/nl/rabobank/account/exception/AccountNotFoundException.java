package nl.rabobank.account.exception;

import nl.rabobank.account.usecase.AccountType;
import nl.rabobank.exception.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
  public AccountNotFoundException(final String document, final AccountType type) {
    super(String.format("Account with type %s and document '%s' not found.", type.name(), document));
  }
}
