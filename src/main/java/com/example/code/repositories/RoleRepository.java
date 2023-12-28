package com.example.code.repositories;

import com.example.code.domain.Role;
import com.example.code.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByType(RoleType type);
}
