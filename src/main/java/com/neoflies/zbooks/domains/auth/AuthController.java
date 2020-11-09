package com.neoflies.zbooks.domains.auth;

import com.neoflies.zbooks.domains.users.User;
import com.neoflies.zbooks.domains.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserRepository userRepository;

  @PostMapping("/register")
  public User registerUser(@Valid @RequestBody RegisterUserPayload payload) {
    User newUser = new User();
    newUser.setEmail(payload.getEmail());
    newUser.setPassword(this.passwordEncoder.encode(payload.getPassword()));
    newUser.setFirstName(payload.getFirstName());
    newUser.setLastName(payload.getLastName());
    return this.userRepository.save(newUser);
  }
}
