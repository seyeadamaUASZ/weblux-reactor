package com.sid.gl.manageemployee.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSION")
@Data
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "permission_seq")
public class Permission extends Auditable<String> {

    @Column(name = "LABEL")
    private String label;

    @Column(name = "VISIBLE_LABEL")
    private String visibleLabel;

    @Column(name = "IS_ACTIVE")
    private boolean isActive = true;

    public Permission() {

    }

    public Permission(String label) {
        this.label = label;
    }

    public Permission(String label, String visibleLabel) {
        this.label = label;
        this.visibleLabel = visibleLabel;
    }
}