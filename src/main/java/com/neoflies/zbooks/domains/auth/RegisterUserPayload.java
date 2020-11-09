package com.neoflies.zbooks.domains.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegisterUserPayload {
  @NotBlank(message = "Please input email")
  @Email(message = "Invalid email address")
  private String email;

  @NotBlank(message = "Please input password")
  private String password;

  @NotBlank(message = "Please input first name")
  private String firstName;

  @NotBlank(message = "Please input last name")
  private String lastName;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
