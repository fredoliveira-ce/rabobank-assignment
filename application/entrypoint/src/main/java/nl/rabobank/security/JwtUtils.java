package nl.rabobank.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.rabobank.security.exception.CurrentUserNotFoundException;
import nl.rabobank.user.usecase.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

public final class JwtUtils {

  private JwtUtils() { }

  private static final String SECRET = "pp.M4J=#9uc)jrL";
  private static final String HEADER_AUTHORIZATION = "Authorization";

  public static void addToken(final HttpServletResponse response, final String username) {
    final String jwt = Jwts.builder()
      .setSubject(username)
      .signWith(SignatureAlgorithm.HS512, SECRET)
      .compact();

    response.addHeader(HEADER_AUTHORIZATION, jwt);
  }

  public static Authentication getAuthentication(final HttpServletRequest request) {
    final String token = request.getHeader(HEADER_AUTHORIZATION);
    Authentication authentication = null;

    if (nonNull(token)) {
      final String username = getUsername(token);

      if (nonNull(username)) {
        authentication = new UsernamePasswordAuthenticationToken(username, null, emptyList());
      }
    }

    return authentication;
  }

  public static User getCurrentUser() {
    var securityContext = SecurityContextHolder.getContext();
    var authentication = securityContext.getAuthentication();
    if (nonNull(authentication)) {
      return User.builder()
        .username(authentication.getPrincipal().toString())
        .build();
    }
    throw new CurrentUserNotFoundException();
  }

  private static String getUsername(final String token) {
    return Jwts.parser()
      .setSigningKey(SECRET)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

}
