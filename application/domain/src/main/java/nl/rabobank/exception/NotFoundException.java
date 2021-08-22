package nl.rabobank.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(final String message) {
    super(message);
  }

  public NotFoundException(final String message, final Exception ex) {
    super(message, ex);
  }
}
