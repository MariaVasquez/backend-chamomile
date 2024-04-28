package org.user.api.userchamomile.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.user.api.userchamomile.dto.GenericResponseDto;
import org.user.api.userchamomile.error.CustomException;
import org.user.api.userchamomile.error.FieldError;
import org.user.api.userchamomile.util.Constants;
import org.user.api.userchamomile.util.JwtUtils;
import org.user.api.userchamomile.util.ResponseCode;

import java.io.IOException;
import java.util.*;


public class JWTValidationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTValidationFilter.class);
    private static final List<FieldError> fieldErrorList = new ArrayList<>();


    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("Init validation token");

        String header = request.getHeader(Constants.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(Constants.PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(Constants.PREFIX_TOKEN, "");
        try {
            Claims claims = Jwts
                    .parser()
                    .verifyWith(Constants.SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String username = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = JwtUtils.parseAuthoritiesFromJwt(authoritiesClaims);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            handleExpiredToken(request, response);
        } catch (JwtException e) {
            handleInvalidToken(request, response);
        } catch (CustomException c) {
            handleCustomException(request, response, c);
        } catch (Exception e) {
            handleGenericException(request, response, e);
        }
    }

    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fieldErrorList.clear();
        fieldErrorList.add(new FieldError("", "Token expired"));
        GenericResponseDto<List<FieldError>> genericResponseDto = new GenericResponseDto<>(ResponseCode.LCO007, ResponseCode.LCO007.getHtmlMessage(), fieldErrorList);

        response.getWriter().write(new ObjectMapper().writeValueAsString( genericResponseDto));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fieldErrorList.clear();
        fieldErrorList.add(new FieldError("", "Invalid token"));
        GenericResponseDto<List<FieldError>> genericResponseDto = new GenericResponseDto<>(ResponseCode.LCO007, ResponseCode.LCO007.getHtmlMessage(), fieldErrorList);

        response.getWriter().write(new ObjectMapper().writeValueAsString( genericResponseDto));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private void handleCustomException(HttpServletRequest request, HttpServletResponse response, CustomException c) throws IOException {
        fieldErrorList.clear();
        fieldErrorList.add(new FieldError("", Arrays.stream(c.getMessage().split(":")).toList().getLast()));
        GenericResponseDto<List<FieldError>> genericResponseDto = new GenericResponseDto<>(ResponseCode.LCO000, ResponseCode.LCO000.getHtmlMessage(), fieldErrorList);

        response.getWriter().write(new ObjectMapper().writeValueAsString( genericResponseDto));
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private void handleGenericException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        fieldErrorList.clear();
        fieldErrorList.add(new FieldError("", Arrays.stream(e.getMessage().split(":")).toList().getLast()));
        GenericResponseDto<List<FieldError>> genericResponseDto = new GenericResponseDto<>(ResponseCode.LCO000, ResponseCode.LCO000.getHtmlMessage(), fieldErrorList);

        response.getWriter().write(new ObjectMapper().writeValueAsString( genericResponseDto));
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
