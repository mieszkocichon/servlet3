package first.servlet.utils;

import java.util.Base64;

public class Cryptography
{
    public static String getBase64FromString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
