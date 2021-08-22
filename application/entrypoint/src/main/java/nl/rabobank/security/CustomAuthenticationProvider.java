package nl.rabobank.security;

import lombok.RequiredArgsConstructor;
import nl.rabobank.user.exception.UserUnauthorizedException;
import nl.rabobank.user.usecase.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  private final UserService service;

  @Override
  protected void additionalAuthenticationChecks(
    final UserDetails userDetails,
    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
  ) throws AuthenticationException { }

  @Override
  protected UserDetails retrieveUser(
    final String username,
    final UsernamePasswordAuthenticationToken authentication
  ) throws AuthenticationException {
    return service.findUser(username, authentication.getCredentials().toString())
      .orElseThrow(() -> new UserUnauthorizedException(username));
  }

}
