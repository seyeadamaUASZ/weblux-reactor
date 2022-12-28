package com.sid.gl.manageemployee.repository;

import com.sid.gl.manageemployee.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByLabel(String label);

}
