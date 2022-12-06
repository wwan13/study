package com.wwan13.studyspringsecurity.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Struct;

@Entity
@Getter @Setter
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String key;

    private String value;

    @Builder
    private RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateToken(String token) {
        this.value = token;
        return this;
    }
}
