package nl.rabobank.api.rest.account;

import nl.rabobank.CommonsTest;
import nl.rabobank.ComponentTest;
import nl.rabobank.template.AccountRequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.is;

@ComponentTest
@DisplayName("Runs all component tests for account api")
public class AccountControllerComponentTest extends CommonsTest {

  private static final String API_PATH = "/api/accounts/";

  @Nested
  @DisplayName("POST " + API_PATH)
  class Save {

    @Test
    @DisplayName("should save a new account")
    void saveNewAccount() {
      // arrange
      createUser();
      var token = doLogin("user4test");
      var request = AccountRequestTemplate.getOne();

      // act
      var response = doPost(API_PATH, request, token);

      // assert
      response
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .body("type", is(request.getType()))
        .body("number", is(request.getNumber()))
        .body("holder_name", is(request.getHolderName()))
        .body("holder_document", is(request.getHolderDocument()));
    }
  }

}
