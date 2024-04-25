package org.user.api.userchamomile.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.user.api.userchamomile.util.Constants;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;


public class JWTValidationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTValidationFilter.class);

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        logger.debug("Init validation token");
        try{
            String header = request.getHeader(Constants.HEADER_AUTHORIZATION);
            if (header == null || !header.startsWith(Constants.PREFIX_TOKEN)) {
                chain.doFilter(request, response);
                return;
            }

            String token = header.replace(Constants.PREFIX_TOKEN, "");

            Claims claims = Jwts.parser().verifyWith(Constants.SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            Object authorizatiesClaims = claims.get("authorizaties");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                    .readValue(authorizatiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request, response);



        }catch (IOException e){
            logger.error("ERROR IN VALIDATE TOKEN: {}",e.getMessage());
            throw new RuntimeException(e);
        } catch (ServletException e) {
            logger.error("ERROR IN VALIDATE TOKEN: {}",e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
