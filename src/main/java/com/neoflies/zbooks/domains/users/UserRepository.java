package com.neoflies.zbooks.domains.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String email);
}
