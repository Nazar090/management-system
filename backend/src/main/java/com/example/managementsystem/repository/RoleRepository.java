package com.example.managementsystem.repository;

import com.example.managementsystem.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a Role by its role name.
     *
     * @param roleName the name of the role to find (e.g., ADMIN, USER)
     * @return an Optional containing the Role if found, or empty if not found
     */
    Optional<Role> findByRole(Role.RoleName roleName);
}
