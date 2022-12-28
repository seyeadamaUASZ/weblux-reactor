package com.sid.gl.manageemployee.security;

import com.sid.gl.manageemployee.models.Employee;
import com.sid.gl.manageemployee.models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserPrincipal  implements UserDetails {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String employeeType;
    private Collection<? extends GrantedAuthority> roles;
    private Collection<? extends GrantedAuthority> permissions;



    public static UserPrincipal createUserPrincipal(Employee user) {

        if (user != null) {
            try {
                List<GrantedAuthority> roles = user.getRoles().stream().filter(Objects::nonNull)
                        .map(role -> new SimpleGrantedAuthority(role.getLabel()))
                        .collect(Collectors.toList());

                List<GrantedAuthority> permissions = user.getRoles().stream().filter(Objects::nonNull)
                        .map(Role::getPermissions).flatMap(Collection::stream)
                        .map(permission -> new SimpleGrantedAuthority(permission.getLabel()))
                        .collect(Collectors.toList());

                return UserPrincipal.builder()
                        .id(user.getId())
                        .name(user.getFirstName() + " " + (user.getLastName()))
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .employeeType(user.getEmployeeType().name())
                        .roles(roles)
                        .permissions(permissions)
                        .build();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return null;
    }

    public static Map<String, Object> toClaims(UserPrincipal user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", String.valueOf(user.getId()));
        claims.put("userName", user.getUsername());
        claims.put("FullName", user.getName());
        claims.put("EmployeeType",user.getEmployeeType());
        claims.put("PERMISSIONS", user.getPermissions().stream().filter(Objects::nonNull)
                .map(permission -> permission.getAuthority())
                .collect(Collectors.toSet()));
        return claims;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
