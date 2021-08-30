package database;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordProtector {

    public String createMD5(String password) {
        return DigestUtils.md5Hex(password);
    }
}
