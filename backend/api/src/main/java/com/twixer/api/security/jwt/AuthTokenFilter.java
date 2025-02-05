package com.twixer.api.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.twixer.api.security.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final String TOKEN_COOKIE_NAME = "accessToken"; // Nombre de la cookie

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = null;
		String username = null;

		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(TOKEN_COOKIE_NAME)) {
					token = cookie.getValue();
				}
			}
		}
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			username = jwtUtils.extractUsername(token);
			if (username != null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtUtils.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}catch(Exception e) {
		    System.err.println("Couldn't be authenticated: " + e.getMessage());

		}

		filterChain.doFilter(request, response);
	}

}
