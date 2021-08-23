package nl.rabobank.account.exception;

import nl.rabobank.exception.NotFoundException;

public class AccountWithoutRegisterException extends NotFoundException {

  public AccountWithoutRegisterException(String holderDocument) {
    super(String.format("Registration for account with document %s not found.", holderDocument));
  }

}
