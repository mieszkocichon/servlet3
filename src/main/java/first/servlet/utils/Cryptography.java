package first.servlet.utils;

import javax.servlet.http.Cookie;
import java.util.Base64;

public class Cryptography
{
    public static String base64EncodeToString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String base64DecodeToBytes(Cookie cookie) {
        return new String(Base64.getDecoder()
                        .decode(cookie.getValue().getBytes()));
    }
}
