package com.example.webfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MemberFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().startsWith("/member-only")) {
			if (!validateMember(request, response)) {
				return;
			}
		}

		if (request.getRequestURI().startsWith("/token-member-only")) {
			if (!validateMember(request, response)) {
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean validateMember(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!StringUtils.hasText(request.getHeader("MEMBER"))) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("Who are you?");
			return false;
		}

		if (request.getHeader("MEMBER").equals("USER")) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Member Only");
			return false;
		}
		return true;
	}



}
