package com.wanted.preonboarding.common.service.password;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptService implements PasswordService {
    @Override
    public String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
