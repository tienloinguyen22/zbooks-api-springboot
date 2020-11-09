package com.neoflies.zbooks.domains.users;

import com.neoflies.zbooks.domains.authorities.Authority;
import com.neoflies.zbooks.domains.authorities.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ApplicationUserDetails implements UserDetails {
  private User user;

  public ApplicationUserDetails(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
    this.user.getAuthorities().forEach((userAuthority) -> {
      grantedAuthorities.add(new SimpleGrantedAuthority(userAuthority.getName()));
    });
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.user.getActive();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.user.getEmailConfirmed();
  }
}
