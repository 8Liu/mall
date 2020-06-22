package com.liudehuang.common.utils;

import com.liudehuang.common.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:31
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:31
 * @UpdateRemark:
 * @Version:
 */
public class JWTUtil {

    /**
     * @param jwtSecretKey JWT加解密秘钥
     * @param expireTime 过期时间(毫秒)
     * @param claims 需要加密的对象(Map结构)
     * @param subject 主题(这里用token唯一标志即可)
     * @return
     */
    public static String createJWT(String jwtSecretKey, long expireTime, Map<String, Object> claims, String subject) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 下面就是在为payload添加各种标准声明和私有声明了
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                // iat: jwt的签发时间
                .setIssuedAt(now)
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userId，roleId之类的
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, new SecretKeySpec(jwtSecretKey.getBytes(),
                        SignatureAlgorithm.HS512.getJcaName()));
        if (expireTime >= 0) {
            long expMillis = nowMillis + expireTime;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return "Bearer " + builder.compact();
    }

    /**
     * @param jwtSecretKey JWT加解密秘钥
     * @param token JwtToken
     * @return
     */
    public static Claims parseJWT(String jwtSecretKey, String token) {
        Key keys = new SecretKeySpec(jwtSecretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(keys)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }


    public static Claims getClaims(String jwtSecretKey,String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            throw new AuthenticationException("登录凭证认证不通过.");
        }
        try {
            return JWTUtil.parseJWT(jwtSecretKey,jwtToken.split("\\s+")[1]);
        } catch (Exception e) {
            throw new AuthenticationException("登录凭证认证不通过.");
        }
    }

    private static Claims parseExpireJWT(String token, String key) {
        Key keys = new SecretKeySpec(key.getBytes(),
                SignatureAlgorithm.HS512.getJcaName());
        Claims claims = new ExpireJwtParser()
                .setSigningKey(keys)
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * @param jwtSecretKey jwt加解密秘钥
     * @param token jwtToken
     * @param subject
     * @param expireTimeMillis
     * @return
     */
    public static String createExpireJWT(String jwtSecretKey,String token, String subject,Long expireTimeMillis) {
        token = token.replaceFirst("Bearer ", "");
        Claims claims = parseExpireJWT(token, jwtSecretKey);
        Long timestamp = Long.valueOf(claims.get("timestamp").toString());
        Long now = System.currentTimeMillis();
        if ((timestamp + expireTimeMillis) < now) {
            throw new AuthenticationException("Token无效或者已经过期");
        }
        Map paramMap = new HashMap();
        for (Map.Entry entry : claims.entrySet()) {
            paramMap.put(entry.getKey(), entry.getValue());
        }
        String jwtToken = createJWT(jwtSecretKey,expireTimeMillis, paramMap, subject);
        jwtToken = "Bearer " + jwtToken;
        return jwtToken;
    }

}