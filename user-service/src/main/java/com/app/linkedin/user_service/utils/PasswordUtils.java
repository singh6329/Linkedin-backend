package com.app.linkedin.user_service.utils;

import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {
    public static String hashPassword(String plainTextPassword)
    {
        return BCrypt.hashpw(plainTextPassword,BCrypt.gensalt());
    }

    public static boolean verifyPassword(String plainTextPassword,String hashedPassword)
    {
        return BCrypt.checkpw(plainTextPassword,hashedPassword);
    }
}
