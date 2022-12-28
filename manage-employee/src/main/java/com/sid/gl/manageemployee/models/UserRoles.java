package com.sid.gl.manageemployee.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "user_api_seq")
public class UserRoles extends Auditable<String> {
    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false)
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
}
