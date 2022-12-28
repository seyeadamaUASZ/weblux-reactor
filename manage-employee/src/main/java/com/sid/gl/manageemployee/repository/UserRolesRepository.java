package com.sid.gl.manageemployee.repository;

import com.sid.gl.manageemployee.models.Role;
import com.sid.gl.manageemployee.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {

    @Query("select u from UserRoles u where u.employee.id = ?1")
    List<UserRoles> findByEmployee_Id(Long id);

    UserRoles findByEmployee_IdAndRole_Id(Long id, Long roleId);

}
