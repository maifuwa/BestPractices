package org.bigboss.springsecuritydemo;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.*;
import lombok.extern.slf4j.Slf4j;
import org.bigboss.springsecuritydemo.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.KeyPair;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午8:15
 * @description: jwt测试
 */
@Slf4j
@SpringBootTest
public class JwtTest {

    /**
     * 生成jws
     */
    @Test
    public void testGenerateToken() {
        SecretKey key = Jwts.SIG.HS384.key().build();

        String token = Jwts.builder()
//                .claim("name", "bigboss")
//                .claim("roles", List.of("admin", "user"))
//                .claims(Map.of("name", "bigboss", "roles", List.of("admin", "user")))
                .header().keyId(getUUID()).and()
                .audience().add("you").and()
                .subject("bigboss")
                .expiration(getExpirationDate())
                .claim("roles", List.of("admin", "user"))
                .signWith(key)
                .id(getUUID())
                .compact();
        log.info("key：{}，token: {}", key, token);
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
    }


    /**
     * 生成并解析jwt
     */
    @Test
    public void testToken() {
        Member member = Member.builder()
                .id(1)
                .username("bigboss")
                .password("123456")
                // .roles(Set.of(new Role(1, "admin"), new Role(2, "user")))
                .build();

        SecretKey key = Jwts.SIG.HS384.key().build();
        String token = Jwts.builder()
                .id(String.valueOf(member.getId()))
                .subject(member.getUsername())
                // .claim("roles", member.getRoles().stream().map(Role::getName).toList())
                .expiration(getExpirationDate())
                .signWith(key)
                .compact();
        log.info("token：{}", token);

        String id = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload().getId();

        log.info("id：{}", id);
    }

    /**
     * 测试解析过期jwt
     */
    @Test
    public void testExp() throws InterruptedException {
        assertThrows(JwtException.class, () -> {
            SecretKey key = Jwts.SIG.HS384.key().build();

            Date now = new Date();
            String token = Jwts.builder()
                    .subject("bigboss")
                    .expiration(new Date(now.getTime() + 10000))
                    .signWith(key)
                    .compact();

            Thread.sleep(10000);

            String sub = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
            log.info("sub: {}", sub);
        });
    }

    /**
     * 测试错误的key
     */
    @Test
    public void testErrorKey() {
        assertThrows(JwtException.class, () -> {
            String token = Jwts.builder()
                    .subject("bigboss")
                    .signWith(Jwts.SIG.HS384.key().build())
                    .compact();

            String sub = Jwts.parser().verifyWith(Jwts.SIG.HS384.key().build()).build().parseSignedClaims(token).getPayload().getSubject();
            log.info("sub: {}", sub);
        });
    }

    /**
     * 测试从文件获取key和非对称加密
     */
    @Test
    public void testKey(@Value("classpath:secret.key") Resource base64key) throws IOException {
         SecretKey secretKey = Keys.hmacShaKeyFor(base64key.getContentAsByteArray());

        SecretKey key = Jwts.SIG.HS384.key().build();
        log.info("key: {}, base64Key: {}", Arrays.toString(key.getEncoded()),Encoders.BASE64.encode(key.getEncoded()));
        KeyPair keyPair = Jwts.SIG.RS256.keyPair().build();
        log.info("PublicKey: {}", keyPair.getPublic());
        log.info("PrivateKey: {}", keyPair.getPrivate());
    }

    /**
     * 生成并解析jws
     */
    @Test
    public void testSigned() {
        // HMAC
        MacAlgorithm macAlgorithm = Jwts.SIG.HS384;
        SecretKey macKey = macAlgorithm.key().build();

        // RSA、ECDSA、EdDSA
        SignatureAlgorithm signatureAlgorithm = Jwts.SIG.RS256;
        KeyPair keyPair = signatureAlgorithm.keyPair().build();
        String jwt = Jwts.builder().subject("bigboss").signWith(keyPair.getPrivate(), signatureAlgorithm).compact();
        String sub = Jwts.parser().verifyWith(keyPair.getPublic()).build().parseSignedClaims(jwt).getPayload().getSubject();

        log.info("jwt: {}, sub: {}", jwt, sub);
    }

    /**
     * 生成并解析jwe
     */
    @Test
    public void testEncrypted() {
        String pw = "correct horse battery staple";
        Password password = Keys.password(pw.toCharArray());
        KeyAlgorithm<Password, Password> alg = Jwts.KEY.PBES2_HS384_A192KW;
        AeadAlgorithm enc = Jwts.ENC.A256GCM;
        String jwe = Jwts.builder().subject("bigboss").encryptWith(password, alg, enc).compact();

        String sub = Jwts.parser().decryptWith(Keys.password(pw.toCharArray()))
                .build().parseEncryptedClaims(jwe).getPayload().getSubject();
        log.info("jwe: {}, sub: {}", jwe, sub);
    }

}