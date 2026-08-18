package com.softdreams.intern.config;

import com.nimbusds.jwt.SignedJWT;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.service.JwtService;
import com.softdreams.intern.service.RolePermissionCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final JwtService jwtService;
    final RolePermissionCacheService rolePermissionCacheService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtToken(request);

        if (StringUtils.hasText(token)) {
            try {
                SignedJWT signedJWT = jwtService.verifyToken(token);
                Long id = signedJWT.getJWTClaimsSet().getLongClaim("id");
                String role = signedJWT.getJWTClaimsSet().getStringClaim("role");

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                if (role != null) {
                    authorities.add(new SimpleGrantedAuthority(role));
                    List<String> permissions = rolePermissionCacheService.getPermissions(role);
                    if (permissions != null) {
                        permissions.stream()
                                .map(SimpleGrantedAuthority::new)
                                .forEach(authorities::add);
                    }
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        id, null, authorities);

                authentication.setDetails(id);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                sendErrorResponse(response, ErrorCode.UNAUTHENTICATED.getMsg());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, String customMessage) throws IOException {
        response.setStatus(ErrorCode.UNAUTHENTICATED.getStatusCode().value());
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format(
                "{\"code\": %s, \"message\": \"%s\"}",
                ErrorCode.UNAUTHENTICATED.getCode(),
                customMessage
        );
        response.getWriter().write(jsonResponse);
    }
}
