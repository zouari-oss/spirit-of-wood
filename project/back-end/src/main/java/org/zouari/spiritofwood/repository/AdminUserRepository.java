package org.zouari.spiritofwood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zouari.spiritofwood.model.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
  Optional<AdminUser> findByEmail(String email);
}
