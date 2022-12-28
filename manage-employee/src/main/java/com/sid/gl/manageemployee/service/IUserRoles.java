package com.sid.gl.manageemployee.service;

import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.models.Role;
import com.sid.gl.manageemployee.models.UserRoles;

public interface IUserRoles {
    public UserRoles addRoleToUser(Long userId, Role role) throws ResourceNotFoundException;
    public void removeUserRole(Long userId,Long roleId);
}
