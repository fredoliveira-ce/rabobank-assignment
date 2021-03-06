package nl.rabobank.user.usecase;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
public class Authority implements GrantedAuthority {

  private String role;

  public Authority(String role) {
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return role;
  }
}
