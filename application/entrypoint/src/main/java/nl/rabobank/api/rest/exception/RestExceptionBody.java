package nl.rabobank.api.rest.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RestExceptionBody {

  private String message;

  private String description;
}
