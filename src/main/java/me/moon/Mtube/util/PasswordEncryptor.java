package me.moon.Mtube.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {
    public static String encrypt(String password){
        String pwd = password;
        return BCrypt.hashpw(pwd, BCrypt.gensalt());
    }

    public static boolean isMatch(String password, String salt){
        String pwd = password;
        return BCrypt.checkpw(pwd, salt);
    }
}
