package mock;

import nl.rabobank.user.usecase.User;

public final class UserMock {

  public static User getOne() {
    return User.builder()
      .username("user-test")
      .password("pwd")
      .document("523490")
      .role("USER")
      .build();
  }
}
