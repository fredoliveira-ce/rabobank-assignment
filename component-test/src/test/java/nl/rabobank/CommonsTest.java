package nl.rabobank;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import nl.rabobank.user.entity.UserEntity;
import nl.rabobank.user.repository.UserMongoRepository;
import nl.rabobank.user.usecase.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.util.UriUtils;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ComponentTest
public class CommonsTest {

  @LocalServerPort
  private int port;

  @Autowired
  private UserMongoRepository mongoRepository;

  public ValidatableResponse doGet(final String path, final String token) {
    return RestAssured.given()
      .port(port)
      .log()
      .all(true)
      .urlEncodingEnabled(false)
      .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
      .header(AUTHORIZATION, token)
      .get(UriUtils.encodePath(path, "UTF-8"))
      .then()
      .log()
      .all(true);
  }

  public ValidatableResponse doPost(final String path, final Object body, final String token) {
    return RestAssured.given()
      .port(port)
      .log()
      .all(true)
      .urlEncodingEnabled(false)
      .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
      .header(AUTHORIZATION, token)
      .body(body)
      .post(path)
      .then()
      .log()
      .all(true);
  }

  public String doLogin(String username) {
    return RestAssured.given()
      .port(port)
      .urlEncodingEnabled(false)
      .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
      .body(User.builder().username(username).password("pwd").build())
      .post("/login")
      .getHeader("Authorization");
  }

  public void createUser() {
    mongoRepository.deleteAll();
    mongoRepository.save(UserEntity.builder()
      .document("000111222")
      .username("user4test")
      .password("pwd")
      .build());
  }

}
