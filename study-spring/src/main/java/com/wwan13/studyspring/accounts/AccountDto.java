package com.wwan13.studyspring.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

public class AccountDto {

    @Data
    @NoArgsConstructor @AllArgsConstructor
    public static final class Request {
        private String id;
        private String pw;
    }

}
