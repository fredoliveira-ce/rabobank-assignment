package nl.rabobank.user.usecase;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  private String username;
  private String password;
  private String document;
  private String role;

  @JsonDeserialize(contentAs=Authority.class)
  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return new HashSet<>(Arrays.asList(new Authority(role)));
  }

  @Override public String getPassword() {
    return password;
  }

  @Override public String getUsername() {
    return username;
  }

  @Override public boolean isAccountNonExpired() {
    return true;
  }

  @Override public boolean isAccountNonLocked() {
    return true;
  }

  @Override public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override public boolean isEnabled() {
    return true;
  }
}
