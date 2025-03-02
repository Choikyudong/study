package com.example.webfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().startsWith("/token-only")) {
			if (!validateToken(request, response)) {
				return;
			}
		}

		if (request.getRequestURI().startsWith("/token-member-only")) {
			if (!validateToken(request, response)) {
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (!StringUtils.hasText(token)) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("token not include");
			return false;
		}
		if (!token.startsWith("Bearer")) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Bearer not include");
			return false;
		}
		if (!token.substring(7).equals("hahah")) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("token is not equlas");
			return false;
		}

		return true;
	}

}
