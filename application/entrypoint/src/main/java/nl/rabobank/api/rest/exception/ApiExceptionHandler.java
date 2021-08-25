package nl.rabobank.api.rest.exception;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.exception.NotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = InvalidParameterException.class)
  protected ResponseEntity<Object> handleInvalidParameter(InvalidParameterException ex, WebRequest request) {
    return handleExceptionInternal(ex, request, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = NotFoundException.class)
  protected ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
    return handleExceptionInternal(ex, request, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = RuntimeException.class)
  protected ResponseEntity<Object> handleRuntime(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(ex, request, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, request, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, request, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    final var description =
        String.format("'%s' is not a valid value for %s of type %s",
            ex.getValue(),
            getPropertyName(ex),
            getType(ex)
        );

    final ApiExceptionBody body = ApiExceptionBody.builder()
                                                    .message(ex.getClass()
                                                               .getSimpleName())
                                                    .description(description)
                                                    .build();

    return handleExceptionInternal(ex, request, HttpStatus.BAD_REQUEST, body);
  }

  private ResponseEntity<Object> handleExceptionInternal(final Exception ex, final WebRequest request,
      final HttpStatus status) {
    final ApiExceptionBody body = ApiExceptionBody.builder()
                                                    .message(ex.getClass()
                                                               .getSimpleName())
                                                    .description(ex.getMessage())
                                                    .build();
    return handleExceptionInternal(ex, request, status, body);
  }

  private ResponseEntity<Object> handleExceptionInternal(final Exception ex, final WebRequest request,
      final HttpStatus status, final ApiExceptionBody body) {
    Sentry.captureException(ex);
    log.error("status=error, error={}", ex.getMessage(), ex);
    return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
  }

  private String getPropertyName(final TypeMismatchException ex) {
    return ex instanceof MethodArgumentTypeMismatchException
        ? ((MethodArgumentTypeMismatchException) ex).getName()
        : ex.getPropertyName();
  }

  private String getType(final TypeMismatchException ex) {
    final var requiredType = ex.getRequiredType();
    if (requiredType != null) {
      return requiredType.getSimpleName();
    } else {
      return null;
    }
  }
}
