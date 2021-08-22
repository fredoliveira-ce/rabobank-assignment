package nl.rabobank.user.exception;

import nl.rabobank.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException(String username) {
    super(String.format("User '%s' not found.", username));
  }
}
