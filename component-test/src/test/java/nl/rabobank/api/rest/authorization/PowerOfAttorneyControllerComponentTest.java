package nl.rabobank.api.rest.authorization;

import nl.rabobank.CommonsTest;
import nl.rabobank.ComponentTest;
import nl.rabobank.account.repository.SavingsAccountMongoRepository;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import nl.rabobank.authorization.repository.PowerOfAttorneyMongoRepository;
import nl.rabobank.template.AccountRequestTemplate;
import nl.rabobank.template.PowerOfAttorneyRequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.is;

@ComponentTest
@DisplayName("Runs all component tests for power of attorney api")
public class PowerOfAttorneyControllerComponentTest extends CommonsTest {

  private static final String API_PATH = "/api/powerofattorneys/";
  private static final String API_PATH_AUTH = API_PATH + "/authorize";
  private static final String ACCOUNTS_API_PATH = "/api/accounts/";

  @Autowired private PowerOfAttorneyDao dao;
  @Autowired private PowerOfAttorneyMongoRepository repository;
  @Autowired private SavingsAccountMongoRepository accountRepository;

  @BeforeEach
  void beforeEach() {
    repository.deleteAll();
    accountRepository.deleteAll();
  }

  @Nested
  @DisplayName("POST " + API_PATH_AUTH + "/authorize")
  class Save {

    @Test
    @DisplayName("should authorize a new power of attorney")
    void authorize() {
      // arrange
      var request = PowerOfAttorneyRequestTemplate.getOneSavingsAccount();
      var token = doLoginAsDefaultUser();
      createGranteeUser(request.getGranteeDocument());
      var grantorUser = createGrantorUserAndAccount(token);
      var grantorToken = doLogin(grantorUser);

      // act
      var response = doPost(API_PATH_AUTH, request, grantorToken);

      // assert
      response
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .body("grantee_document", is(request.getGranteeDocument()))
        .body("authorization", is(request.getAuthorization()));
    }
  }

  @Nested
  @DisplayName("GET " + API_PATH)
  class Find {

    @Test
    @DisplayName("should return a list of accounts an logged user has access for")
    void findAuthorizedAccounts() {
      // arrange
      var request = PowerOfAttorneyRequestTemplate.getOneSavingsAccount();
      var token = doLoginAsDefaultUser();
      var granteeUser = createGranteeUser(request.getGranteeDocument());
      createGrantorUserAndAccount(token);
      var granteeToken = doLogin(granteeUser);
      dao.save(request.toDomain("7033502"));

      // act
      var response = doGet(API_PATH, granteeToken);

      // assert
      response
        .assertThat()
        .statusCode(HttpStatus.OK.value());
    }
  }

  private String createGrantorUserAndAccount(String token) {
    var grantorLogin = "grantor.test";
    var grantorDocument = "7033502";
    var request = AccountRequestTemplate.getOneSavingsAccount(grantorDocument);

    createUser(grantorLogin, grantorDocument);

    doPost(ACCOUNTS_API_PATH, request, token)
      .assertThat()
      .statusCode(HttpStatus.CREATED.value())
      .body("type", is(request.getType()))
      .body("number", is(request.getNumber()))
      .body("holder_name", is(request.getHolderName()))
      .body("holder_document", is(request.getHolderDocument()));

    return grantorLogin;
  }

  private String doLoginAsDefaultUser() {
    createDefaultUser();
    return doLogin(DEFAULT_USER);
  }

  private String createGranteeUser(String granteeDocument) {
    var granteeUser = "grantee.test";
    createUser(granteeUser, granteeDocument);
    return granteeUser;
  }

}
