package com.sid.gl.manageemployee.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ROLE")
@Data
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "role_seq")
public class Role extends Auditable<String> {

    @Column(name = "LABEL")
    private String label;

    @Column(name = "VISIBLE_LABEL")
    private String visibleLabel;

    @Column(name = "IS_ACTIVE")
    private boolean isActive = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    private Collection<Permission> permissions;

    public Role() {

    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String label) {
        this.label = label;
    }

    public Role(String label, Collection<Permission> permissions) {
        this.label = label;
        this.permissions = permissions;
    }

    public void disable() {
        this.setActive(false);
    }

    public void enable() {
        this.setActive(true);
    }
}
