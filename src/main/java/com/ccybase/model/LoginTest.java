package com.ccybase.model;

import lombok.Data;

@Data
public class LoginTest {
    private Integer id;
    private String mobile;
    private  String password;
    private String expected;
}
