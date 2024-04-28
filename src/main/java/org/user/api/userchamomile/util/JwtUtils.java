package org.user.api.userchamomile.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.user.api.userchamomile.error.CustomException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static Collection<? extends GrantedAuthority> parseAuthoritiesFromJwt(Object authoritiesClaims) {
        if (authoritiesClaims == null) {
            logger.error("Authorities claims is null");
            return Collections.emptyList();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (authoritiesClaims instanceof String) {
                List<Map<String, String>> authorities = objectMapper.readValue((String) authoritiesClaims, new TypeReference<>() {});
                return authorities.stream()
                        .map(authorityMap -> new SimpleGrantedAuthority(authorityMap.get("authority")))
                        .collect(Collectors.toList());
            }else{
                logger.error("Authorities claims is not a string");
                throw new CustomException(ResponseCode.LCO000, ResponseCode.LCO000.getHtmlMessage());

            }
        } catch (Exception e) {
            logger.error("Error parsing authorities claims", e);
            throw new CustomException(ResponseCode.LCO000, ResponseCode.LCO000.getHtmlMessage());
        }
    }

    public static String readJWT(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String jws = token.substring(7);
        return Jwts.parser().verifyWith(Constants.SECRET_KEY).build().parseSignedClaims(jws).getPayload().getSubject();
    }
}
