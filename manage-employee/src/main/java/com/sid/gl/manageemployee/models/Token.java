package com.sid.gl.manageemployee.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TOKEN")
@Data
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "token_seq")
public class Token extends Auditable<String> {

    @Column(name = "ACCESS_TOKEN", nullable = false)
    @Lob
    private String accessToken;
    @Column(name = "REFRESH_TOKEN", nullable = false)
    @Lob
    private String refreshToken;

    @Column(name = "USERNAME", nullable = false)
    private String username;

}
