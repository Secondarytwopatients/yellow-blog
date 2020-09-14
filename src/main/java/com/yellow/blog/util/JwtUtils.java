package com.yellow.blog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Huang
 */
@Slf4j
@Data
@Component
public class JwtUtils {
    @Value("${yellow.jwt.secret}")
    public String secret;
    @Value("${yellow.jwt.expire}")
    public long expire;
    @Value("${yellow.jwt.header}")
    public String header;

    /**
     * 生成jwt
     * @param userId
     * @return
     */
    public String generateToken(Long userId){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime()+ expire*1000);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }


    /**
     * 校验token
     * @param token
     * @return
     */
    public Claims getClaimsByToken(String token ){

        try{
            return  Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

        }catch (Exception e){
            log.debug("validate is token error",e);
            return null;
        }


    }

    /**
     * 校验token是否过期
     *
     * @return true:过期
     */

    public boolean isTokenExpired(Date expiration){
        return expiration.before(new Date());
    }
}
