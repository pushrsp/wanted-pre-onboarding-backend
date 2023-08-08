package com.wanted.preonboarding.common.service.password;

public interface PasswordService {
    String hash(String plainPassword);
    boolean verify(String plainPassword, String hashedPassword);
}
