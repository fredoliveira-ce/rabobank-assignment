package nl.rabobank.api.rest.authorization;

import io.sentry.spring.tracing.SentryTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.account.Account;
import nl.rabobank.authorization.PowerOfAttorneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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

  @SentryTransaction(operation = "save-power-of-attorney")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public PowerOfAttorneyResponse save(@RequestBody @Valid PowerOfAttorneyRequest request) {
    log.info("Request to save a new object.");
    return from(service.save(request.toDomain()));
  }

  @GetMapping(value = "/{user}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Account>> find(final @PathVariable String user) {
    log.debug("Request to get account access: {}.", user);

    List<Account> accounts = service.find(user);

    return accounts.isEmpty()
      ? notFound().build()
      : ok().body(accounts);
  }

}
