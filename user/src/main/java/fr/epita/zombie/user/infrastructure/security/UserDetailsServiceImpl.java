package fr.epita.zombie.user.infrastructure.security;

import fr.epita.zombie.user.domain.ports.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final IUserRepository userRepository;

  public UserDetailsServiceImpl(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(username)
        .map(UserDetailsConnected::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }
}
