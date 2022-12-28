package com.sid.gl.manageemployee.service;

import com.sid.gl.manageemployee.models.Permission;
import com.sid.gl.manageemployee.models.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IRole {
     Optional<Role> findById(Long id);
     Optional<Role> findByLabel(String label);
     Set<Permission> getPermissions(Set<Role> roles);
     Set<Role> findRolesByLabel(Set<String> labels);
     Role saveRole(Role role);
     List<Role> findAll();
}
