package com.sid.gl.manageemployee.security;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public CustomSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean hasAnyPermission(String... permissions) throws Exception {
        UserPrincipal authentication = (UserPrincipal) getPrincipal();
        for (String permission : permissions) {
            if (authentication.getPermissions()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(a -> a.equals(permission))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPermission(String... permissions) throws Exception {
        UserPrincipal authentication = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (CollectionUtils.isNotEmpty(authentication.getPermissions())) {
            List<String> authenticationPermissions = authentication.getPermissions()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return Arrays.stream(permissions)
                    .filter(StringUtils::isNotBlank)
                    .allMatch(permission -> authenticationPermissions.contains(permission));
        }
        return false;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }

    public void setThis(Object target) {
        this.target = target;
    }
}
