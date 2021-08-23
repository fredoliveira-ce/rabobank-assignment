package nl.rabobank.api.rest.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.authorization.usecase.Authorization;
import nl.rabobank.authorization.usecase.PowerOfAttorneyService;
import nl.rabobank.authorization.usecase.PowerOfAttorneyValidator;
import nl.rabobank.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static nl.rabobank.api.rest.authorization.PowerOfAttorneyResponse.from;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/powerofattorneys")
public class PowerOfAttorneyController {

  private final PowerOfAttorneyService service;
  private final PowerOfAttorneyValidator validator;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = "/authorize", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public PowerOfAttorneyResponse save(@RequestBody @Valid final PowerOfAttorneyRequest request) {
    log.info("Request to save a new power of attorney.");
    var user = validator.validate(
      request.getGranteeDocument(),
      JwtUtils.getCurrentUser().getUsername(),
      Authorization.valueOf(request.getAuthorization())
    );

    return from(service.save(request.toDomain(user.getDocument())));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PowerOfAttorneyResponse>> findAuthorizedAccounts() {
    var permissions = service.find(JwtUtils.getCurrentUser().getUsername()).stream()
      .map(PowerOfAttorneyResponse::from)
      .collect(Collectors.toList());

    return permissions.isEmpty()
      ? notFound().build()
      : ok().body(permissions);
  }

}
