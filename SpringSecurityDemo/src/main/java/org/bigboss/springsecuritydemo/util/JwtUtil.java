package org.bigboss.springsecuritydemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:37
 * @description: jwt工具类
 */
public class JwtUtil {

    private final static String pw = "correct horse battery staple";

    private final static Long exp = 60480000L;

    public static String generateToken(Integer id, String name) {
        return "Bearer " + generateJwe(id, name);
    }

    private static String generateJwe(Integer id, String name) {
        return Jwts.builder()
                .id(String.valueOf(id))
                .subject(name)
                .expiration(buildExpirationDate())
                .encryptWith(Keys.password(pw.toCharArray()), Jwts.KEY.PBES2_HS256_A128KW, Jwts.ENC.A128CBC_HS256)
                .compressWith(Jwts.ZIP.GZIP)
                .compact();
    }

    private static Date buildExpirationDate() {
        return new Date(System.currentTimeMillis() + exp);
    }

    public static Claims parseToken(String token) {
        token = token.replace("Bearer ", "");
        return getClaims(token);
    }

    public static Integer getId(String token) {
        return Integer.valueOf(parseToken(token).getId());
    }

    public static String getSubject(String token) {
        return parseToken(token).getSubject();
    }

    public static Long getRemainingExp(String token) {
        Date exp = parseToken(token).getExpiration();
        Date now = new Date();
        return exp.after(now) ? exp.getTime() - now.getTime() : 0;
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .decryptWith(Keys.password(pw.toCharArray()))
                .build()
                .parseEncryptedClaims(token)
                .getPayload();
    }

}
