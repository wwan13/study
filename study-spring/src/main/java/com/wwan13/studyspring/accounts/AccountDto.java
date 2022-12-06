package com.wwan13.studyspring.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AccountDto {
    private String id;
    private String password;
}
