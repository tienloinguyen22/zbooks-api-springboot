package com.neoflies.zbooks.domains.authorities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority {
  @Id
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
