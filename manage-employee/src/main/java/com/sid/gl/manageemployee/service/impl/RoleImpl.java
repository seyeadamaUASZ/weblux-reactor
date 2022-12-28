package com.sid.gl.manageemployee.service.impl;

import com.sid.gl.manageemployee.models.Permission;
import com.sid.gl.manageemployee.models.Role;
import com.sid.gl.manageemployee.repository.RoleRepository;
import com.sid.gl.manageemployee.service.IRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleImpl implements IRole {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByLabel(String label) {
        return roleRepository.findByLabel(label);
    }

    @Override
    public Set<Permission> getPermissions(Set<Role> roles) {
        return roles.stream()
                .filter(Objects::nonNull)
                .map(Role::getPermissions)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Role> findRolesByLabel(Set<String> labels) {
        return labels.stream()
                .map(this::findByLabel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll() ;
    }
}
