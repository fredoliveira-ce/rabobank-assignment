package nl.rabobank.security.exception;

import nl.rabobank.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrentUserNotFoundException extends NotFoundException {
  public CurrentUserNotFoundException() {
    super("There is no user logged at this moment.");
  }
}
