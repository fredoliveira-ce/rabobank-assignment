package nl.rabobank.api.rest.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiExceptionBody {

  private String message;

  private String description;
}
