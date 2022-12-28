package com.sid.gl.manageemployee.service.impl;

import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.models.Employee;
import com.sid.gl.manageemployee.models.Role;
import com.sid.gl.manageemployee.models.UserRoles;
import com.sid.gl.manageemployee.repository.EmployeeRepository;
import com.sid.gl.manageemployee.repository.RoleRepository;
import com.sid.gl.manageemployee.repository.UserRolesRepository;
import com.sid.gl.manageemployee.service.IUserRoles;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleImpl implements IUserRoles {

    private final UserRolesRepository userRolesRepository;
    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    @SneakyThrows
    @Override
    public UserRoles addRoleToUser(Long userId, Role role)  {
        Optional<Employee> optionalEmployee =
        employeeRepository.findById(userId);
        if(optionalEmployee.isEmpty())
            throw new ResourceNotFoundException("User with id not found ");

        Employee employee = optionalEmployee.get();
        UserRoles userRoles = UserRoles.builder()
                .role(role)
                .employee(employee)
                .build();

        return userRolesRepository.save(userRoles);
    }

    @SneakyThrows
    @Override
    public void removeUserRole(Long userId, Long roleId) {
      Optional<Role> optionalRole = roleRepository.findById(roleId);
      if(optionalRole.isEmpty())
          throw new ResourceNotFoundException("role with id not found ");
      UserRoles userRoles = userRolesRepository.findByEmployee_IdAndRole_Id(userId,roleId);
      userRolesRepository.delete(userRoles);

    }
}
