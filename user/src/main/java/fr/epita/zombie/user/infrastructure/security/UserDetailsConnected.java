package fr.epita.zombie.user.infrastructure.security;

import fr.epita.zombie.user.infrastructure.models.UserModel;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsConnected implements UserDetails {
  private final Long id;
  private final String email;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsConnected(UserModel user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
